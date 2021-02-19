package com.gift.service;

import com.gift.model.entities.Category;
import com.gift.model.entities.Product;
import com.gift.model.entities.Transaction;
import com.gift.model.projections.Choice;
import com.gift.model.projections.Count;
import com.gift.repository.ProductRepo;
import com.gift.repository.ProductTransactionRepo;
import com.gift.repository.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.*;

/**
 * Service for the transactions products
 *
 * @author Maksim Shcherbakov
 */
@Service
public class ProductTransactionService {
    private final ProductTransactionRepo productTransactionRepo;
    private final ProductRepo productRepo;
    private final TransactionRepo transactionRepo;
    private final ProductService productService;

    @Autowired
    public ProductTransactionService(ProductTransactionRepo productTransactionRepo, ProductRepo productRepo,
                                     TransactionRepo transactionRepo, ProductService productService) {
        this.productTransactionRepo = productTransactionRepo;
        this.productRepo = productRepo;
        this.transactionRepo = transactionRepo;
        this.productService = productService;
    }

    @Transactional
    public void save (Choice choice) {
        Transaction transaction = new Transaction();
        transaction.setRecipient(choice.getRecipient());
        transactionRepo.save(transaction);

        Set<Product> products = new HashSet<>(Arrays.asList(choice.getProducts()));

        for(Product product : products) {
            productTransactionRepo.save(transaction.getId(), product.getId());
            productRepo.promotionCounter(product.getId());
        }
    }

    public List<Product> findProduct (Set<Category> categories) {
        HashMap<Product, BigInteger> productsMap = new HashMap<>();
        HashMap<Product, BigInteger> allProductsMap = new HashMap<>();
        List<Product> products = new ArrayList<>();
        List<Count> counts = productTransactionRepo.findCountProduct();

        for (Count count : counts) {
            allProductsMap.put(productRepo.findProductById(Long.valueOf(count.getProduct_Id())), count.getCount());
        }

        if (categories.isEmpty()) {
            for (Map.Entry<Product, BigInteger> e : allProductsMap.entrySet()) {
                productsMap.put(e.getKey(), e.getValue());
            }
        } else {
            for (Category category : categories) {
                for (Map.Entry<Product, BigInteger> e : allProductsMap.entrySet()) {
                    if (e.getKey() != null && Objects.equals((Long) e.getKey().getCategory().getId(), category.getId())) {
                        productsMap.put(e.getKey(), e.getValue());
                    }
                }
            }
        }

        productsMap.entrySet().stream().sorted(Map.Entry.<Product, BigInteger>comparingByValue().reversed())
                        .forEach(productBigIntegerEntry -> products.add(productBigIntegerEntry.getKey()));

        List<Product> resultProducts = new ArrayList<>(productService.checkCounter(products));

        if (resultProducts.size() > 5) {
            return new ArrayList<>(productService.checkCounter(products).subList(0, 6));
        }

        return resultProducts;
    }
}

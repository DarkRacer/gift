package com.gift.service;

import com.gift.model.entities.Category;
import com.gift.model.entities.Product;
import com.gift.model.entities.Transaction;
import com.gift.model.entities.Users;
import com.gift.model.projections.Count;
import com.gift.model.projections.GiftCriteria;
import com.gift.repository.CategoriesRepo;
import com.gift.repository.ProductRepo;
import com.gift.repository.ProductTransactionRepo;
import com.gift.repository.TransactionRepo;
import com.gift.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Service for the products
 *
 * @author Maksim Shcherbakov
 */
@Service
public class ProductService {
    private final ProductRepo productRepo;
    private final ProductTransactionRepo productTransactionRepo;
    private final UserRepository userRepository;
    private final TransactionRepo transactionRepo;
    private final CategoriesRepo categoriesRepo;

    @Autowired
    public ProductService(ProductRepo productRepo, ProductTransactionRepo productTransactionRepo, UserRepository userRepository, TransactionRepo transactionRepo, CategoriesRepo categoriesRepo) {
        this.productRepo = productRepo;
        this.productTransactionRepo = productTransactionRepo;
        this.userRepository = userRepository;
        this.transactionRepo = transactionRepo;
        this.categoriesRepo = categoriesRepo;
    }

    @javax.transaction.Transactional
    public List<Product> checkCounter (List<Product> products) {
        List<Product> resultProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.getCounter() >= 5) {
                productRepo.removeCounter(product.getId());
                continue;
            }
            resultProducts.add(product);
        }

        return resultProducts;
    }

    @Transactional
    public List<Product> findProduct (GiftCriteria giftCriteria) {
        HashMap<Product, BigInteger> productsMap = new HashMap<>();
        HashMap<Product, BigInteger> allProductsMap = new HashMap<>();
        List<Count> counts = productTransactionRepo.findCountProduct();
        List<Product> resultProducts = new ArrayList<>();

        Users users = userRepository.findUsersByProviderUserId(giftCriteria.getUserId());

        if(users != null) {
            List<Transaction> transactions = transactionRepo.findTransactionsWish(users.getId());
            if (transactionRepo.findTransactionsWish(users.getId()) != null) {
                List<Long> productId = new ArrayList<>();

                for (Transaction transaction : transactions) {
                    productId.addAll(productTransactionRepo.findProductTransactionsByTransaction(transaction.getId()));
                }

                for (Long id : productId) {
                    resultProducts.add(productRepo.findProductById(id));
                }
            }
        }

        for (Count count : counts) {
            allProductsMap.put(productRepo.findProductById(Long.valueOf(count.getProduct_Id())), count.getCount());
        }

        if (giftCriteria.getCategories().isEmpty()) {
            for (Map.Entry<Product, BigInteger> e : allProductsMap.entrySet()) {
                productsMap.put(e.getKey(), e.getValue());
            }
        } else {
            for (Category category : giftCriteria.getCategories()) {
                for (Map.Entry<Product, BigInteger> e : allProductsMap.entrySet()) {
                    if (e.getKey() != null && Objects.equals(e.getKey().getCategory().getId(), category.getId())) {
                        productsMap.put(e.getKey(), e.getValue());
                    }
                }
            }
        }

        productsMap.entrySet().stream().sorted(Map.Entry.<Product, BigInteger>comparingByValue().reversed())
                .forEach(productBigIntegerEntry -> resultProducts.add(productBigIntegerEntry.getKey()));

        if (resultProducts.size() > 5) {
            return new ArrayList<>(checkCounter(resultProducts).subList(0, 6));
        }

        return resultProducts;
    }

    public List<Product> findByCategories(List<String> categoriesName) {
        List<Category> categories = new ArrayList<>();
        List<Product> products = new ArrayList<>();

        System.out.println(categoriesName);

        for (String categoryName : categoriesName) {
            categories.add(categoriesRepo.findCategoriesByName(categoryName));
        }

        for (Category category : categories) {
            if (category != null) {
                products.addAll(productRepo.findByCategory(category.getId()));
            }
        }

        return products;
    }
}

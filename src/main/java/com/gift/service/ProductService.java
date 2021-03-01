package com.gift.service;

import com.gift.model.entities.Category;
import com.gift.model.entities.Product;
import com.gift.model.projections.Count;
import com.gift.repository.ProductRepo;
import com.gift.repository.ProductTransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Service for the products
 *
 * @author Maksim Shcherbakov
 */
@Service
public class ProductService {
    private final ProductRepo productRepo;
    private final ProductTransactionRepo productTransactionRepo;

    @Autowired
    public ProductService(ProductRepo productRepo, ProductTransactionRepo productTransactionRepo) {
        this.productRepo = productRepo;
        this.productTransactionRepo = productTransactionRepo;
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
                    if (e.getKey() != null && Objects.equals(e.getKey().getCategory().getId(), category.getId())) {
                        productsMap.put(e.getKey(), e.getValue());
                    }
                }
            }
        }

        productsMap.entrySet().stream().sorted(Map.Entry.<Product, BigInteger>comparingByValue().reversed())
                .forEach(productBigIntegerEntry -> products.add(productBigIntegerEntry.getKey()));

        List<Product> resultProducts = new ArrayList<>(checkCounter(products));

        if (resultProducts.size() > 5) {
            return new ArrayList<>(checkCounter(products).subList(0, 6));
        }

        return resultProducts;
    }
}

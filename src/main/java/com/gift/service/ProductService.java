package com.gift.service;

import com.gift.model.entities.Product;
import com.gift.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Service for the products
 *
 * @author Maksim Shcherbakov
 */
@Service
public class ProductService {
    private final ProductRepo productRepo;

    @Autowired
    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    @Transactional
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
}

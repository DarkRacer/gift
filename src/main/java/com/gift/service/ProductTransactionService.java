package com.gift.service;

import com.gift.model.entities.Product;
import com.gift.model.entities.Transaction;
import com.gift.model.projections.Choice;
import com.gift.repository.ProductRepo;
import com.gift.repository.ProductTransactionRepo;
import com.gift.repository.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    @Autowired
    public ProductTransactionService(ProductTransactionRepo productTransactionRepo, ProductRepo productRepo,
                                     TransactionRepo transactionRepo) {
        this.productTransactionRepo = productTransactionRepo;
        this.productRepo = productRepo;
        this.transactionRepo = transactionRepo;
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
}

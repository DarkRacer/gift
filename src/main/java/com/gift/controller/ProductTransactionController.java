package com.gift.controller;

import com.gift.model.projections.Choice;
import com.gift.service.ProductTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for the transactions products
 *
 * @author Maksim Shcherbakov
 */
@CrossOrigin
@RestController
@RequestMapping("/transaction")
public class ProductTransactionController {
    private final ProductTransactionService productTransactionService;

    @Autowired
    public ProductTransactionController(ProductTransactionService productTransactionService) {
        this.productTransactionService = productTransactionService;
    }

    @PostMapping("/save")
    public void save (@RequestBody Choice choice) {
        productTransactionService.save(choice);
    }
}

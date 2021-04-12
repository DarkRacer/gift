package com.gift.controller;

import com.gift.model.entities.Product;
import com.gift.model.projections.Choice;
import com.gift.model.projections.InfoTransaction;
import com.gift.model.projections.SelectionsHistory;
import com.gift.service.ProductTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

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

    @PostMapping("/wish")
    public Set<Product> loadWish(@RequestBody Long userId) {
        return productTransactionService.loadWish(userId);
    }

    @PostMapping("/history")
    public List<SelectionsHistory> getHistory(@RequestBody Long userId){
        return productTransactionService.getHistory(userId);
    }

    @PostMapping("/wish/delete")
    public void deleteWish (@RequestBody InfoTransaction transactionWish){
        productTransactionService.deleteWish(transactionWish);
    }

    @PostMapping("/wish/save")
    public void saveWish (@RequestBody InfoTransaction transactionWish){
        productTransactionService.saveWish(transactionWish);
    }
    @PostMapping("/selectionsHistory/delete")
    public void deleteSelectionsHistory (@RequestBody InfoTransaction transaction){
        productTransactionService.deleteSelectionsHistory(transaction);
    }

    @PostMapping("/selectionsHistory/save")
    public void saveSelectionsHistory(@RequestBody InfoTransaction transaction){
        productTransactionService.saveSelectionsHistory(transaction);
    }


}

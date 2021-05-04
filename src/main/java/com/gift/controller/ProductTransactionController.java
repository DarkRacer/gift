package com.gift.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gift.model.projections.Choice;
import com.gift.model.projections.InfoTransaction;
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
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public ProductTransactionController(ProductTransactionService productTransactionService) {
        this.productTransactionService = productTransactionService;
    }

    @PostMapping("/save")
    public void save (@RequestBody String choice) throws JsonProcessingException {
        productTransactionService.save(objectMapper.readValue(choice, Choice.class));
    }

    @PostMapping("/wish")
    public String loadWish(@RequestBody String userId) throws JsonProcessingException {
        return objectMapper.writeValueAsString(productTransactionService.loadWish(objectMapper.readValue(userId, Long.class)));
    }

    @PostMapping("/history")
    public String getHistory(@RequestBody String userId) throws JsonProcessingException {
        return objectMapper.writeValueAsString(productTransactionService.getHistory(objectMapper.readValue(userId, Long.class)));
    }

    @PostMapping("/wish/delete")
    public void deleteWish (@RequestBody String transactionWish) throws JsonProcessingException {
        productTransactionService.deleteWish(objectMapper.readValue(transactionWish, InfoTransaction.class));
    }

    @PostMapping("/wish/save")
    public void saveWish (@RequestBody String transactionWish) throws JsonProcessingException {
        productTransactionService.saveWish(objectMapper.readValue(transactionWish, InfoTransaction.class));
    }
    @PostMapping("/selectionsHistory/delete")
    public void deleteSelectionsHistory (@RequestBody String transaction) throws JsonProcessingException {
        productTransactionService.deleteSelectionsHistory(objectMapper.readValue(transaction, InfoTransaction.class));
    }

    @PostMapping("/selectionsHistory/save")
    public void saveSelectionsHistory(@RequestBody String transaction) throws JsonProcessingException {
        productTransactionService.saveSelectionsHistory(objectMapper.readValue(transaction, InfoTransaction.class));
    }


}

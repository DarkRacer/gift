package com.gift.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gift.model.projections.GiftCriteria;
import com.gift.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for the products
 *
 * @author Maksim Shcherbakov
 */
@CrossOrigin
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/find")
    public String findProduct (@RequestBody String giftCriteria) throws JsonProcessingException {
        return objectMapper.writeValueAsString(productService.findProduct(objectMapper.readValue(giftCriteria, GiftCriteria.class)));
    }
}

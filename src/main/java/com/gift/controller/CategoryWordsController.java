package com.gift.controller;

import com.gift.model.entities.Product;
import com.gift.service.CategoryWordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * Controller for the words categories
 *
 * @author Maksim Shcherbakov
 */
@CrossOrigin
@RestController
@RequestMapping("/words")
public class CategoryWordsController {
    private final CategoryWordsService categoryWordsService;

    @Autowired
    public CategoryWordsController(CategoryWordsService categoryWordsService) {
        this.categoryWordsService = categoryWordsService;
    }

    @PostMapping("/find")
    public Set<Product> find (@RequestBody String userUrl) throws InterruptedException {
        return categoryWordsService.find(userUrl);
    }
}

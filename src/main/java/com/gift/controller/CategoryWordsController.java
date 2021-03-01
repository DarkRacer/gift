package com.gift.controller;

import com.gift.model.projections.SelectedCategory;
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
@RequestMapping("/categories")
public class CategoryWordsController {
    private final CategoryWordsService categoryWordsService;

    @Autowired
    public CategoryWordsController(CategoryWordsService categoryWordsService) {
        this.categoryWordsService = categoryWordsService;
    }

    @PostMapping("/find")
    public Set<SelectedCategory> find (@RequestBody String[] topics) {
        return categoryWordsService.findCategories(topics);
    }
}

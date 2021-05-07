package com.gift.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gift.model.projections.WordCategories;
import com.gift.service.CategoriesService;
import com.gift.service.CategoryWordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final CategoriesService categoriesService;

    @Autowired
    public CategoryWordsController(CategoryWordsService categoryWordsService, CategoriesService categoriesService) {
        this.categoryWordsService = categoryWordsService;
        this.categoriesService = categoriesService;
    }

    @PostMapping("/find")
    public String find (@RequestBody String topics) throws JsonProcessingException {
        return objectMapper.writeValueAsString(categoryWordsService.findCategories(objectMapper.readValue(topics, new TypeReference<String[]>(){})));
    }

    @GetMapping("/all")
    public String findAll () throws JsonProcessingException {
        return objectMapper.writeValueAsString(categoryWordsService.findAll());
    }

    @GetMapping("/category/all")
    public String findCategoryAll () throws JsonProcessingException {
        return objectMapper.writeValueAsString(categoriesService.findAll());
    }

    @PostMapping("/save")
    public void save (@RequestBody String categoryWords) throws JsonProcessingException {
        categoryWordsService.save(objectMapper.readValue(categoryWords, WordCategories.class));
    }
}

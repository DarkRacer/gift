package com.gift.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public CategoryWordsController(CategoryWordsService categoryWordsService) {
        this.categoryWordsService = categoryWordsService;
    }

    @PostMapping("/find")
    public String find (@RequestBody String topics) throws JsonProcessingException {
        return objectMapper.writeValueAsString(categoryWordsService.findCategories(objectMapper.readValue(topics, new TypeReference<String[]>(){})));
    }

    @GetMapping("/all")
    public String findAll () throws JsonProcessingException {
        return objectMapper.writeValueAsString(categoryWordsService.findAll());
    }
}

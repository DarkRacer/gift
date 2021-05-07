package com.gift.service;

import com.gift.model.entities.Category;
import com.gift.repository.CategoriesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for the categories
 *
 * @author Maksim Shcherbakov
 */
@Service
public class CategoriesService {
    private final CategoriesRepo categoriesRepo;

    @Autowired
    public CategoriesService(CategoriesRepo categoriesRepo) {
        this.categoriesRepo = categoriesRepo;
    }

    public Category findById (Long id) {
        return categoriesRepo.findById(id).orElse(null);
    }

    public List<Category> findAll () { return categoriesRepo.findAll();}
}

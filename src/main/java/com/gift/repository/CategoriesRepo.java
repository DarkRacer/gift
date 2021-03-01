package com.gift.repository;

import com.gift.model.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for the categories
 *
 * @author Maksim Shcherbakov
 */
public interface CategoriesRepo extends JpaRepository<Category, Long> {
    Category findCategoriesById(Long id);
}

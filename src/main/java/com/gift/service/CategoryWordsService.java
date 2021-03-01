package com.gift.service;

import com.gift.api.MegaIndex;
import com.gift.api.VK;
import com.gift.model.api.megaIndex.Data;
import com.gift.model.api.megaIndex.Post;
import com.gift.model.api.megaIndex.Topic;
import com.gift.model.api.vk.Items;
import com.gift.model.entities.Category;
import com.gift.model.entities.CategoryWord;
import com.gift.model.entities.Product;
import com.gift.model.projections.SelectedCategory;
import com.gift.repository.CategoriesRepo;
import com.gift.repository.CategoryWordsRepo;
import com.gift.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Service for the words categories
 *
 * @author Maksim Shcherbakov
 */
@Service
public class CategoryWordsService {
    private final CategoryWordsRepo categoryWordsRepo;
    private final CategoriesRepo categoriesRepo;

    @Autowired
    public CategoryWordsService(CategoryWordsRepo categoryWordsRepo, CategoriesRepo categoriesRepo) {
        this.categoryWordsRepo = categoryWordsRepo;
        this.categoriesRepo = categoriesRepo;
    }

    public Set<SelectedCategory> findCategories (String[] topics) {
        List<Category> categories = categoriesRepo.findAll();
        Set<SelectedCategory> selectedCategories = new HashSet<>();
        Set<String> newWords = new HashSet<>(Arrays.asList(topics));


        for (Category category : categories) {
            List<String> words = new ArrayList<>();
            Set<String> setTopics = new HashSet<>();
            for (CategoryWord categoryWord : categoryWordsRepo.findCategoryWordsByCategory_Id(category.getId())) {
                words.add(categoryWord.getWord());
            }

            for (String word : words) {
                for (String topic : topics) {
                    if (word.toLowerCase().equals(topic.toLowerCase())){
                        setTopics.add(word);
                        newWords.remove(word);
                    }
                }
            }

            if (!setTopics.isEmpty()){
                selectedCategories.add(new SelectedCategory(category.getId(),
                        category.getName(),
                        new ArrayList<>(setTopics)));
                newWords.removeAll(setTopics);
            }
        }

        for (String newW : newWords) {
            categoryWordsRepo.save(newW);
        }

        if (selectedCategories.isEmpty()) {
            Category category = categoriesRepo.findCategoriesById((long) 1);
            selectedCategories.add(new SelectedCategory(category.getId(), category.getName(), new ArrayList<>(newWords)));
        }

        return selectedCategories;
    }
}

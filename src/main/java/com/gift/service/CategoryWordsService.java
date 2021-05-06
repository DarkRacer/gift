package com.gift.service;


import com.gift.model.entities.Category;
import com.gift.model.entities.CategoryWord;
import com.gift.model.projections.SelectedCategory;
import com.gift.model.projections.WordCategories;
import com.gift.repository.CategoriesRepo;
import com.gift.repository.CategoryWordsRepo;
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
                selectedCategories.add(new SelectedCategory(category,
                        new ArrayList<>(setTopics)));
                newWords.removeAll(setTopics);
            }
        }

        for (String newW : newWords) {
            categoryWordsRepo.save(newW);
        }

        if (selectedCategories.isEmpty()) {
            Category category = categoriesRepo.findCategoriesById((long) 1);
            selectedCategories.add(new SelectedCategory(category, new ArrayList<>(newWords)));
        }

        return selectedCategories;
    }

    public List<WordCategories> findAll() {
        List<CategoryWord> categoryWords = categoryWordsRepo.findAll();
        List<WordCategories> wordCategoriesList = new ArrayList<>();

        for (CategoryWord categoryWord : categoryWords) {
            if (check(categoryWord, wordCategoriesList)) {
                WordCategories wordCategories = new WordCategories();
                List<Category> categories = new ArrayList<>();

                for (CategoryWord categoryWordCheck : categoryWords) {
                    if (Objects.equals(categoryWord.getWord().toLowerCase(), categoryWordCheck.getWord().toLowerCase())) {
                        categories.add(categoryWordCheck.getCategory());
                    }
                }

                wordCategories.setWord(categoryWord.getWord());
                wordCategories.setCategories(categories);
                wordCategoriesList.add(wordCategories);
            }
        }

        return wordCategoriesList;
    }

    private boolean check (CategoryWord categoryWord, List<WordCategories> wordCategoriesList) {
        if (wordCategoriesList != null) {
            for (WordCategories wordCategories : wordCategoriesList) {
                if (categoryWord.getWord().toLowerCase().equals(wordCategories.getWord().toLowerCase())) {
                    return false;
                }
            }
        }

        return true;
    }
}

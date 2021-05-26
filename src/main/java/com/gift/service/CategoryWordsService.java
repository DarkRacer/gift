package com.gift.service;


import com.gift.model.entities.Category;
import com.gift.model.entities.CategoryWord;
import com.gift.model.projections.SelectedCategory;
import com.gift.model.projections.WordCategories;
import com.gift.repository.CategoriesRepo;
import com.gift.repository.CategoryWordsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    @Transactional
    public Set<SelectedCategory> findCategories (String[] topics) {
        List<Category> categories = categoriesRepo.findAll();
        Set<SelectedCategory> selectedCategories = new HashSet<>();
        Set<String> newWords = new HashSet<>(Arrays.asList(topics));


        for (Category category : categories) {
            List<String> words = new ArrayList<>();
            Set<String> setTopics = new HashSet<>();
            for (CategoryWord categoryWord : categoryWordsRepo.findCategoryWordsById_Category(category.getId())) {
                words.add(categoryWord.getId().getWord());
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
            categoryWordsRepo.saveNew(newW);
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
                    if (Objects.equals(categoryWord.getId().getWord().toLowerCase(), categoryWordCheck.getId().getWord().toLowerCase())) {
                        categories.add(categoryWordCheck.getId().getCategory());
                    }
                }
                wordCategories.setWord(categoryWord.getId().getWord());
                wordCategories.setCategories(categories);
                wordCategoriesList.add(wordCategories);
            }
        }

        return wordCategoriesList;
    }

    private boolean check (CategoryWord categoryWord, List<WordCategories> wordCategoriesList) {
        if (wordCategoriesList != null) {
            for (WordCategories wordCategories : wordCategoriesList) {
                if (categoryWord.getId().getWord().toLowerCase().equals(wordCategories.getWord().toLowerCase())) {
                    return false;
                }
            }
        }

        return true;
    }

    @Transactional
    public void save(WordCategories wordCategories) {
        List<CategoryWord> categoryWords = categoryWordsRepo.findCategoryWordsByWord(wordCategories.getWord());
        Set<Category> categoriesForSave = new HashSet<>(wordCategories.getCategories());

        for (CategoryWord categoryWord : categoryWords) {
            categoriesForSave.remove(categoryWord.getId().getCategory());
        }

        for (Category category : categoriesForSave) {
            categoryWordsRepo.save(category.getId(), wordCategories.getWord());
        }

        categoryWords = categoryWordsRepo.findCategoryWordsByWord(wordCategories.getWord());
        if (categoryWords.size() != wordCategories.getCategories().size()) {
            List<Category> categories = new ArrayList<>();

            for (CategoryWord categoryWord : categoryWords) {
                categories.add(categoryWord.getId().getCategory());
            }

            for (Category category : wordCategories.getCategories())
                categories.remove(category);

            for (Category category : categories) {
                categoryWordsRepo.deleteByCategoryAndWord(category.getId(), wordCategories.getWord());
            }
        }

    }
}

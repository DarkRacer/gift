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
    private final ProductRepo productRepo;
    private final MegaIndex megaIndex;
    private final VK vk;
    private final ProductTransactionService productTransactionService;

    @Autowired
    public CategoryWordsService(CategoryWordsRepo categoryWordsRepo, ProductRepo productRepo, MegaIndex megaIndex,
                                VK vk, ProductTransactionService productTransactionService) {
        this.categoryWordsRepo = categoryWordsRepo;
        this.productRepo = productRepo;
        this.megaIndex = megaIndex;
        this.vk = vk;
        this.productTransactionService = productTransactionService;
    }

    public Set<Product> find (String userUrl) throws InterruptedException {
        List<String> descriptions = new ArrayList<>();
        for (Items items : vk.findUserSubscriptions(userUrl).getResponse().getItems()) {
            descriptions.add(items.getDescription());
        }

        List<String> topicsList = new ArrayList<>();

        descriptions.removeAll(Arrays.asList("", null));

        for(String description : descriptions) {
            if (description.length() > 1500) {
                description = description.substring(0, 1500);
            }
             Post post = megaIndex.definingTopic(description);
             Data[] data = post.getData();
             Topic[] topics = data[0].getTopics();

             for(Topic topic : topics) {
                 topicsList.add(topic.getTopic());
             }
            Thread.sleep(200);
         }

        List<CategoryWord> categoryWords = categoryWordsRepo.findAll();
        Set<Category> selectedCategories = new HashSet<>();
        Set<String> newWords = new HashSet<>();

        for (String word : topicsList){
            newWords.add(word);
            for (CategoryWord wordCat : categoryWords){
                if (word.toLowerCase().equals(wordCat.getWord().toLowerCase())){
                    selectedCategories.add(wordCat.getCategory());
                    newWords.remove(word);
                }
            }
        }

        for (String newW : newWords) {
            categoryWordsRepo.save(newW);
        }

//      Remove when there is a sufficient number of transactions
        Set<Product> products = new HashSet<>();
        if (selectedCategories.isEmpty()) {
            products.addAll(productRepo.findByCategory((long) 4));
        } else {
            for (Category category : selectedCategories) {
                products.addAll(productRepo.findByCategory(category.getId()));
            }
        }

//      Use it when there is a sufficient number of transactions
//      Set<Product> products = new HashSet<>(productTransactionService.findProduct(selectedCategories));

        return products;
    }
}

package com.gift.service;


import com.gift.api.MegaIndex;
import com.gift.api.VK;
import com.gift.model.api.megaIndex.Data;
import com.gift.model.api.megaIndex.Post;
import com.gift.model.api.megaIndex.Topic;
import com.gift.model.api.vk.Items;
import com.gift.model.api.vk.ResponseUserGet;
import com.gift.model.entities.Category;
import com.gift.model.entities.Product;
import com.gift.model.projections.Assortment;
import com.gift.model.projections.Group;
import com.gift.repository.CategoriesRepo;
import com.gift.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service for the gifts
 *
 * @author Maksim Shcherbakov
 */
@Service
public class GiftService {
    private final MegaIndex megaIndex;
    private final VK vk;
    private final CategoriesRepo categoriesRepo;
    private final ProductRepo productRepo;

    @Autowired
    public GiftService(MegaIndex megaIndex, VK vk, CategoriesRepo categoriesRepo, ProductRepo productRepo) {
        this.megaIndex = megaIndex;
        this.vk = vk;
        this.categoriesRepo = categoriesRepo;
        this.productRepo = productRepo;
    }

    public ResponseUserGet findUser (String userUrl) {
        return vk.findUserId(userUrl).getResponse()[0];
    }

    public Set<Group> findGroups (String userUrl) throws InterruptedException {
        Set<Group> groupSet = new HashSet<>();
        for (Items items : vk.findUserSubscriptions(userUrl).getResponse().getItems()) {
            if (!Objects.equals(items.getDescription(), "") && items.getDescription() != null)
                groupSet.add(new Group(items));
        }

        Set<Group> groups = groupSet.stream().limit(10).collect(Collectors.toCollection(HashSet::new));

        for (Group group : groups) {
            if (group.getDescription().length() > 1400) {
                group.setDescription(group.getDescription().substring(0, 1400));
            }
            Post post = megaIndex.definingTopic(group.getDescription());
            Data[] data = post.getData();
            Topic[] topics = data[0].getTopics();

            List<String> topicsList = new ArrayList<>();
            for (Topic topic : topics) {
                topicsList.add(topic.getTopic());
            }
            group.setTopics(topicsList);

            if (group.getDescription().length() > 150) {
                group.setDescription(group.getDescription().substring(0, 150) + "...");
            }
            Thread.sleep(10);
        }

        return groups;
    }

    public List<Assortment> selectSelf (List<Product> products) {
        List<Category> categories = categoriesRepo.findAll();
        List<Assortment> assortments = new ArrayList<>();

        for(Category category : categories) {
            assortments.add(new Assortment(category, productRepo.findByCategory(category.getId())));
        }

        for (Assortment assortment : assortments) {
            assortment.getProducts().removeAll(products);
        }
        return assortments;
    }
}

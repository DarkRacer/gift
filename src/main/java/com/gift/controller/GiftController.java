package com.gift.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gift.model.api.vk.ResponseUserGet;
import com.gift.model.entities.Product;
import com.gift.model.projections.Assortment;
import com.gift.model.projections.Group;
import com.gift.service.GiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * Controller for the gifts
 *
 * @author Maksim Shcherbakov
 */
@CrossOrigin
@RestController
@RequestMapping("/gifts")
public class GiftController {
    private final GiftService giftService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public GiftController(GiftService giftService) {
        this.giftService = giftService;
    }

    @PostMapping("/find/groups")
    public String findGroups (@RequestBody String userUrl) throws InterruptedException, JsonProcessingException {
        return objectMapper.writeValueAsString(giftService.findGroups(userUrl));
    }

    @PostMapping("/find/user")
    public String findUser (@RequestBody String userUrl) throws JsonProcessingException {
        return objectMapper.writeValueAsString(giftService.findUser(userUrl));
    }

    @PostMapping("/self")
    public String selectSelf (@RequestBody String products) throws JsonProcessingException {
        return objectMapper.writeValueAsString(giftService.selectSelf(objectMapper.readValue(products, new TypeReference<List<Product>>() {})));
    }
}

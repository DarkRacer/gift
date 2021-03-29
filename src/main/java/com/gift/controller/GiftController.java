package com.gift.controller;

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

    @Autowired
    public GiftController(GiftService giftService) {
        this.giftService = giftService;
    }

    @GetMapping("/g")
    public String g () {
        System.out.println(1);
        return "work";
    }

    @PostMapping("/find/groups")
    public Set<Group> findGroups (@RequestBody String userUrl) throws InterruptedException {
        return giftService.findGroups(userUrl);
    }

    @PostMapping("/find/user")
    public ResponseUserGet findUser (@RequestBody String userUrl) {
        return giftService.findUser(userUrl);
    }

    @PostMapping("/self")
    public List<Assortment> selectSelf (@RequestBody List<Product> products) {
        return giftService.selectSelf(products);
    }
}

package com.gift.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gift.model.entities.Users;
import com.gift.model.projections.UserAndRole;
import com.gift.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UsersController {
    private final UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/{id}")
    public String getInfo (@PathVariable("id") Long id) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.writeValueAsString(usersService.getInfo(id));
    }

    @GetMapping("/all")
    public String getAll () throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.writeValueAsString(usersService.getAll());
    }

    @PostMapping("/saveAdmin")
    public void saveAdmin (@RequestBody String userAndRole) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        usersService.saveAdmin(objectMapper.readValue(userAndRole, UserAndRole.class));
    }

    @PostMapping("/deleteAdmin")
    public void deleteAdmin (@RequestBody String userAndRole) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        usersService.deleteAdmin(objectMapper.readValue(userAndRole, UserAndRole.class));
    }

    @GetMapping("/roles/{id}")
    public String getRoles (@PathVariable("id") Long id) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.writeValueAsString(usersService.getRoles(id));
    }
}

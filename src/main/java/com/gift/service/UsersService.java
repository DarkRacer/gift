package com.gift.service;

import com.gift.model.entities.Users;
import com.gift.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    private final UserRepository userRepository;

    @Autowired
    public UsersService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Users getInfo (Long id) {
        return userRepository.findUsersById(id);
    }
}

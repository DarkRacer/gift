package com.gift.repository;

import com.gift.model.entities.AuthenticationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthenticationRepo extends JpaRepository<AuthenticationEntity, Long> {
    AuthenticationEntity findByCodeAndSid(String code, String sid);

    AuthenticationEntity findByUserId(Long userId);
}

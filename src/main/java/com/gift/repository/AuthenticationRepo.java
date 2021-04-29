package com.gift.repository;

import com.gift.model.entities.AuthenticationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthenticationRepo extends JpaRepository<AuthenticationEntity, Long> {
    AuthenticationEntity findByCodeAndUuid(String code, String uuid);

    AuthenticationEntity findByUserId(Long userId);
}

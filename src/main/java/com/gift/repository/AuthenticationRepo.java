package com.gift.repository;

import com.gift.model.entities.AuthenticationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface AuthenticationRepo extends JpaRepository<AuthenticationEntity, Long> {
    @Query(value = "select * from gift.authentication where code = :code and uuid = :uuid", nativeQuery = true)
    AuthenticationEntity findByCodeAndUuid(@Param("code") String code, @Param("uuid") String uuid);

    AuthenticationEntity findByUserId(Long userId);

}

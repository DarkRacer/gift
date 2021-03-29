package com.gift.repository;

import com.gift.model.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

	Users findByEmail(String email);

	boolean existsByEmail(String email);

	Users findUsersByProviderUserId(Integer id);

	Users findUsersById (Long id);

	boolean existsById(Long id);
}

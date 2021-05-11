package com.gift.repository;


import com.gift.model.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findByName(String name);

	@Query(value = "delete from gift.user_role ur where ur.user_id = :id and ur.role_id = 2", nativeQuery = true)
	void deleteAdmin(@Param("id") Long id);
}

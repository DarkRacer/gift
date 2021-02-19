package com.gift.repository;

import com.gift.model.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository for the products
 *
 * @author Maksim Shcherbakov
 */
public interface ProductRepo extends JpaRepository<Product, Long> {
    @Query(value = "select p.* from gift.products p where p.category_id = :id", nativeQuery = true)
    List<Product> findByCategory(@Param("id") Long id);

    Product findProductById(Long id);

    @Modifying
    @Query(value = "update gift.products set counter = counter + 1 where id = :id", nativeQuery = true)
    void promotionCounter(@Param("id") Long id);

    @Modifying
    @Query(value = "update gift.products set counter = 0 where id = :id", nativeQuery = true)
    void removeCounter(@Param("id") Long id);
}

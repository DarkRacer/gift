package com.gift.repository;

import com.gift.model.entities.CategoryWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Repository for the words categories
 *
 * @author Maksim Shcherbakov
 */
public interface CategoryWordsRepo extends JpaRepository<CategoryWord, Long> {
    @Query(value = "select * from gift.category_words", nativeQuery = true)
    List<CategoryWord> findAll ();

    @Query(value = "select * from gift.category_words where category_id = :id", nativeQuery = true)
    List<CategoryWord> findCategoryWordsById_Category(@Param("id")Long id);

    @Modifying
    @Query(value = "insert into gift.category_words (category_id, word) values (1, :word)", nativeQuery = true)
    void saveNew(@Param("word") String word);

    @Query(value = "select * from gift.category_words where word = :word", nativeQuery = true)
    List<CategoryWord> findCategoryWordsByWord(@Param("word")String word);

    @Modifying
    @Query(value = "insert into gift.category_words (category_id, word) values (:id, :word)", nativeQuery = true)
    void save(@Param("id") Long id, @Param("word") String word);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "delete from gift.category_words where category_id = :categoryId and word = :word", nativeQuery = true)
    void deleteByCategoryAndWord(@Param("categoryId") Long categoryId, @Param("word") String word);
}

package com.gift.repository;

import com.gift.model.entities.CategoryWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository for the words categories
 *
 * @author Maksim Shcherbakov
 */
public interface CategoryWordsRepo extends JpaRepository<CategoryWord, Long> {
    @Query(value = "select * from gift.category_words", nativeQuery = true)
    List<CategoryWord> findAll ();

    List<CategoryWord> findCategoryWordsByCategory_Id(Long id);

    @Modifying
    @Query(value = "insert into gift.category_words (category_id, word) values (1, :word)", nativeQuery = true)
    void saveNew(@Param("word") String word);

    List<CategoryWord> findCategoryWordsByWord(String word);

    @Modifying
    @Query(value = "insert into gift.category_words (category_id, word) values (:id, :word)", nativeQuery = true)
    void save(@Param("id") Long id, @Param("word") String word);

    CategoryWord findCategoryWordByCategory_IdAndWord(Long id, String word);
}

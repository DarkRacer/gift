package com.gift.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Class describes the category words entity
 *
 * @author Maksim Shcherbakov
 */
@Entity
@Table(schema = "gift", name = "category_words")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryWord {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "word")
    private String word;

    @ManyToOne(targetEntity = Category.class)
    @JoinColumn(name = "category_id")
    private Category category;
}

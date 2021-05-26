package com.gift.model.entities;

import com.gift.model.embedded.CategoryWordEmbedded;
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
    @EmbeddedId
    private CategoryWordEmbedded id;
}

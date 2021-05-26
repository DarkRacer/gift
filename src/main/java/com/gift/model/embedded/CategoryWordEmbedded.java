package com.gift.model.embedded;

import com.gift.model.entities.Category;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Data
@Embeddable
public class CategoryWordEmbedded implements Serializable {
    @ManyToOne(targetEntity = Category.class)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "word")
    private String word;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryWordEmbedded that = (CategoryWordEmbedded) o;
        return category.equals(that.category) &&
                word.equals(that.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category, word);
    }
}

package com.gift.model.projections;

import com.gift.model.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WordCategories {
    private String word;
    private List<Category> categories;
}

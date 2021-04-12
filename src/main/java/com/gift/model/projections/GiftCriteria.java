package com.gift.model.projections;

import com.gift.model.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GiftCriteria {
    private Set<Category> categories;
    private Integer userId;
}

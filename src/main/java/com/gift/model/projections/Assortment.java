package com.gift.model.projections;

import com.gift.model.entities.Category;
import com.gift.model.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Projection to the assortment of products in the categories
 *
 * @author Maksim Shcherbakov
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Assortment {
    private Category category;
    private List<Product> products;
}

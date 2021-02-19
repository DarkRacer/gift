package com.gift.model.projections;

import com.gift.model.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Projection to save the products selected by the user
 *
 * @author Maksim Shcherbakov
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Choice {
    private String recipient;
    private Product[] products;
}

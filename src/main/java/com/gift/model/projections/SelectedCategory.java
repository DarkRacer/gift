package com.gift.model.projections;

import com.gift.model.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Projection for the selected category
 *
 * @author Maksim Shcherbakov
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelectedCategory {
    private Category category;
    private List<String> topics;
}

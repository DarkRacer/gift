package com.gift.model.projections;

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
    private Long id;
    private String name;
    private List<String> topics;
}

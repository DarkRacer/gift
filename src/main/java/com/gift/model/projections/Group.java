package com.gift.model.projections;

import com.gift.model.api.vk.Items;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Projection for groups
 *
 * @author Maksim Shcherbakov
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Group {
    private String name;
    private String screen_name;
    private String description;
    private String photo_50;
    private String photo_200;
    private List<String> topics;

    public Group(Items items) {
        this.name = items.getName();
        this.screen_name = items.getScreen_name();
        this.description = items.getDescription();
        this.photo_50 = items.getPhoto_50();
        this.photo_200 = items.getPhoto_200();
    }
}

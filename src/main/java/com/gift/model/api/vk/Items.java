package com.gift.model.api.vk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class for processing the "items" response field of the method users.getSubscriptions vk API
 *
 * @author Maksim Shcherbakov
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Items {
    private Long id;
    private String name;
    private String screen_name;
    private Long is_closed;
    private String type;
    private String description;
    private String photo_50;
    private String photo_100;
    private String photo_200;
}

package com.gift.model.api.vk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class for processing the "first_name", "id", "last_name" response fields of the method users.get vk API
 *
 * @author Maksim Shcherbakov
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseUserGet {
    private String first_name;
    private Long id;
    private String last_name;
}

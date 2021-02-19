package com.gift.model.api.vk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class for processing the "count", "items" response fields of the method users.getSubscriptions vk API
 *
 * @author Maksim Shcherbakov
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseUserGetSubscriptions {
    private Long count;
    private Items[] items;
}

package com.gift.model.api.vk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class for processing the "response" response field of the method users.getSubscriptions vk API
 *
 * @author Maksim Shcherbakov
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseGroup {
    private ResponseUserGetSubscriptions response;
}

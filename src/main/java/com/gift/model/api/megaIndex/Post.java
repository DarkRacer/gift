package com.gift.model.api.megaIndex;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

/**
 * Class for processing the "status", "data", "request_time" response fields of the method visrep/lda_content
 * megaIndex API
 *
 * @author Maksim Shcherbakov
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    private Long status;
    private Data[] data;
    private double request_time;
}

package com.gift.model.api.megaIndex;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class for processing the "topics" response field of the method visrep/lda_content megaIndex API
 *
 * @author Maksim Shcherbakov
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
public class Data {
    private Topic[] topics;
}

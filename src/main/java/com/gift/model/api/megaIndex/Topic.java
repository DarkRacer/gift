package com.gift.model.api.megaIndex;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

/**
 * Class for processing the "i", "t", "n", w response fields of the method visrep/lda_content
 * megaIndex API
 *
 * @author Maksim Shcherbakov
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
public class Topic {
    private Long i;
    private String t;
    private String n;
    private double w;

    public String getTopic() {
        return n.split("/")[0];
    }
}

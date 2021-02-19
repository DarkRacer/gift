package com.gift.api;

import com.gift.model.api.megaIndex.Post;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Class for interacting with the API https://ru.megaindex.com/
 *
 * @author Maksim Shcherbakov
 */
@Component
public class MegaIndex {
    @Value("${megaIndex.url}")
    private String megaIndexUrl;
    @Value("${megaIndex.api.key}")
    private String apiKey;
    private final RestTemplate restTemplate = new RestTemplate();

    public Post definingTopic (String content) {
        Map<String, String> vars = new HashMap<>();
        vars.put("key", apiKey);
        vars.put("content", content);

        return restTemplate.getForObject(megaIndexUrl + "visrep/lda_content?key={key}&content={content}",
                Post.class, vars);
    }
}

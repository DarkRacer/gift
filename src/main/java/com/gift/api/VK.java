package com.gift.api;

import com.gift.model.api.vk.ResponseGroup;
import com.gift.model.api.vk.ResponseUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Class for interacting with the API https://vk.com/
 *
 * @author Maksim Shcherbakov
 */
@Component
public class VK {
    @Value("${vk.api.url}")
    private String vkUrl;
    @Value("${vk.api.version}")
    private String version;
    @Value("${vk.api.access_token}")
    private String accessToken;
    @Value("${spring.security.oauth2.client.registration.vk.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.vk.redirect-uri}")
    private String redirectUri;
    private RestTemplate restTemplate = new RestTemplate();

    public ResponseUser findUserId (String userUrl) {
        Map<String, String> vars = new HashMap<>();
        vars.put("user_ids", userUrl.substring(15));
        vars.put("fields", "photo_100");
        vars.put("lang", "ru");
        vars.put("access_token", accessToken);
        vars.put("v", version);

        return restTemplate.getForObject(vkUrl + "users.get?user_ids={user_ids}&fields={fields}&lang={lang}" +
                "&access_token={access_token}&v={v}", ResponseUser.class, vars);
    }

    public ResponseGroup findUserSubscriptions (String userUrl) {
        Map<String, String> vars = new HashMap<>();
        vars.put("user_id", String.valueOf(findUserId(userUrl).getResponse()[0].getId()));
        vars.put("extended", "1");
        vars.put("fields", "description");
        vars.put("access_token", accessToken);
        vars.put("v", version);

        return restTemplate.getForObject(vkUrl + "users.getSubscriptions?user_id={user_id}&extended={extended}&" +
                "fields={fields}&access_token={access_token}&v={v}", ResponseGroup.class, vars);
    }
}

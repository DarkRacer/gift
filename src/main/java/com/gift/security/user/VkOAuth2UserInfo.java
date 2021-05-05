package com.gift.security.user;

import java.util.Map;

public class VkOAuth2UserInfo extends OAuth2UserInfo {

    public VkOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return  String.valueOf(attributes.get("id"));
    }

    @Override
    public String getName() {
        return attributes.get("first_name") + " " + attributes.get("last_name");
    }

    @Override
    public String getEmail() {
        return null;
    }

    @Override
    public String getImage() {
        return (String) attributes.get("photo_max_orig");
    }
}

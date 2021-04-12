package com.gift.security;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequestEntityConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.net.URI;

@Component
class CustomRequestEntityConverter implements
        Converter<OAuth2AuthorizationCodeGrantRequest, RequestEntity<?>> {

    @Value("${spring.security.oauth2.client.registration.vk.client-secret}")
    private String clientSecret;


    private OAuth2AuthorizationCodeGrantRequestEntityConverter defaultConverter;

    public CustomRequestEntityConverter() {
        defaultConverter = new OAuth2AuthorizationCodeGrantRequestEntityConverter();
    }

    @Override
    public RequestEntity<?> convert(OAuth2AuthorizationCodeGrantRequest req) {

        RequestEntity<?> entity = defaultConverter.convert(req);
        MultiValueMap<String, String> params = (MultiValueMap<String, String>) entity.getBody();
        String url = String.valueOf(entity.getUrl());

        url += "?client_id=" + params.get("client_id")
                + "&client_secret=" + "8XzMe6W9BlK7b8tkv7Im"
                + "&redirect_uri=" + params.get("redirect_uri").toString()
                + "&code=" + params.get("code").toString();
        url = url.replace("[", "").replace("]", "");

        return new RequestEntity<>(entity.getHeaders(),
                    HttpMethod.GET, URI.create(url));

    }

}

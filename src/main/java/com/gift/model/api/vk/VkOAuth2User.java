package com.gift.model.api.vk;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class VkOAuth2User implements OAuth2User {
    private List<GrantedAuthority> authorities =
            AuthorityUtils.createAuthorityList("ROLE_USER");
    private Map<String, Object> attributes;
    private Integer id;
    private String firstName;
    private String lastName;
    private String picture;

    public VkOAuth2User(Map<String, Object> attributes) {
        this.id = (Integer) attributes.get("id");
        this.firstName = (String) attributes.get("first_name");
        this.lastName = (String) attributes.get("last_name");
        this.picture = (String) attributes.get("photo_50");
    }

    @Override
    public Map<String, Object> getAttributes() {
        if (this.attributes == null) {
            this.attributes = new HashMap<>();
            this.attributes.put("id", this.getId());
            this.attributes.put("first_name", this.getFirstName());
            this.attributes.put("last_name", this.getLastName());
            this.attributes.put("picture", this.getPicture());
        }
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public String getName () {
        return this.firstName + " " + this.lastName;
    }
}

package com.gift.service;


import com.gift.exception.UserAlreadyExistAuthenticationException;
import com.gift.model.entities.Users;
import com.gift.model.projections.LocalUser;
import com.gift.security.SignUpRequest;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

import java.util.Map;

public interface UserService {

	public Users registerNewUser(SignUpRequest signUpRequest) throws UserAlreadyExistAuthenticationException;

	Users findUserByEmail(String email);

	Users findUsersByProviderUserId(Integer id);

	LocalUser processUserRegistration(String registrationId, Map<String, Object> attributes, OidcIdToken idToken, OidcUserInfo userInfo);
}

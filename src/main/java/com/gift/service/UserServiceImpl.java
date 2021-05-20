package com.gift.service;

import com.gift.exception.OAuth2AuthenticationProcessingException;
import com.gift.exception.UserAlreadyExistAuthenticationException;
import com.gift.model.entities.Role;
import com.gift.model.entities.Users;
import com.gift.model.projections.LocalUser;
import com.gift.model.projections.SocialProvider;
import com.gift.repository.RoleRepository;
import com.gift.repository.UserRepository;
import com.gift.security.SignUpRequest;
import com.gift.security.user.OAuth2UserInfo;
import com.gift.security.user.OAuth2UserInfoFactory;
import com.gift.util.GeneralUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;


@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Override
	@Transactional(value = "transactionManager")
	public Users registerNewUser(final SignUpRequest signUpRequest) throws UserAlreadyExistAuthenticationException {
		if (signUpRequest.getUserID() != null && userRepository.existsById(signUpRequest.getUserID())) {
			throw new UserAlreadyExistAuthenticationException("User with User id " + signUpRequest.getUserID() + " already exist");
		} else if (userRepository.existsByEmail(signUpRequest.getEmail()) && !Objects.equals(signUpRequest.getSocialProvider().getProviderType(), "vk")) {
			throw new UserAlreadyExistAuthenticationException("User with email id " + signUpRequest.getEmail() + " already exist");
		}
		Users user = buildUser(signUpRequest);
		Date now = Calendar.getInstance().getTime();
		user = userRepository.save(user);
		return user;
	}

	private Users buildUser(final SignUpRequest formDTO) {
		Users user = new Users();
		user.setProviderUserId(Integer.parseInt(formDTO.getProviderUserId()));
		user.setFirstName(formDTO.getDisplayName().substring(0, formDTO.getDisplayName().indexOf(" ")));
		user.setLastName(formDTO.getDisplayName().substring(formDTO.getDisplayName().indexOf(" ")));
		user.setEmail(formDTO.getEmail());
		user.setPicture(formDTO.getPicture());
		final HashSet<Role> roles = new HashSet<Role>();
		roles.add(roleRepository.findByName(Role.ROLE_USER));
		user.setRoles(roles);
		user.setProvider(formDTO.getSocialProvider().getProviderType());
		return user;
	}

	@Override
	public Users findUserByEmail(final String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	@Transactional
	public LocalUser processUserRegistration(String registrationId, Map<String, Object> attributes, OidcIdToken idToken, OidcUserInfo userInfo) {
		OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, attributes);
		if (StringUtils.isEmpty(oAuth2UserInfo.getName())) {
			throw new OAuth2AuthenticationProcessingException("Name not found from OAuth2 provider");
		} else if (StringUtils.isEmpty(oAuth2UserInfo.getEmail()) && !Objects.equals(registrationId, "vk")) {
			throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
		}
		SignUpRequest userDetails = toUserRegistrationObject(registrationId, oAuth2UserInfo);
		Users user;
		if (Objects.equals(registrationId, "vk")) {
			user = findUsersByProviderUserId(Integer.valueOf(oAuth2UserInfo.getId()));
		} else {
			user = findUserByEmail(oAuth2UserInfo.getEmail());
		}

		if (user != null) {
			if (!user.getProvider().equals(registrationId) && !user.getProvider().equals(SocialProvider.LOCAL.getProviderType())) {
				throw new OAuth2AuthenticationProcessingException(
						"Looks like you're signed up with " + user.getProvider() + " account. Please use your " + user.getProvider() + " account to login.");
			}
			user = updateExistingUser(user, oAuth2UserInfo);
		} else {
			user = registerNewUser(userDetails);
		}

		return LocalUser.create(user, attributes, idToken, userInfo);
	}

	private Users updateExistingUser(Users existingUser, OAuth2UserInfo oAuth2UserInfo) {
		existingUser.setPicture(oAuth2UserInfo.getImage());
		return userRepository.save(existingUser);
	}

	private SignUpRequest toUserRegistrationObject(String registrationId, OAuth2UserInfo oAuth2UserInfo) {
		return SignUpRequest.getBuilder().addProviderUserID(oAuth2UserInfo.getId()).addDisplayName(oAuth2UserInfo.getName()).addEmail(oAuth2UserInfo.getEmail())
				.addSocialProvider(GeneralUtils.toSocialProvider(registrationId)).addPicture(oAuth2UserInfo.getImage()).build();
	}

	@Override
	public Users findUsersByProviderUserId(Integer id) {
		return userRepository.findUsersByProviderUserId(id);
	}

	@Override
	public Users findUsersByUserId(Long id) {
		return userRepository.findUsersById(id);
	}
}

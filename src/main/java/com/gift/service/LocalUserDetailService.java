package com.gift.service;

import com.gift.model.entities.Users;
import com.gift.model.projections.LocalUser;
import com.gift.util.GeneralUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service("localUserDetailService")
public class LocalUserDetailService implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Override
	@Transactional
	public LocalUser loadUserByUsername(final String username) throws UsernameNotFoundException {
		Users user;
		if (username.contains("@")) {
			user = userService.findUserByEmail(username);
			if (user == null) {
				throw new UsernameNotFoundException("User " + username + " was not found in the database");
			}
		} else {
			user = userService.findUsersByProviderUserId(Integer.valueOf(username));
			if (user == null) {
				throw new UsernameNotFoundException("User " + username + " was not found in the database");
			}
		}
		return createLocalUser(user);
	}

	@Transactional
	public LocalUser loadUserById(Integer id) {
		Users user = userService.findUsersByProviderUserId(id);
		return createLocalUser(user);
	}

	private LocalUser createLocalUser(Users user) {
		if (Objects.equals(user.getProvider(), "vk")) {
			return new LocalUser(String.valueOf(user.getId()), true, true, true, GeneralUtils.buildSimpleGrantedAuthorities(user.getRoles()), user);
		} else {
			return new LocalUser(user.getEmail(), true, true, true, GeneralUtils.buildSimpleGrantedAuthorities(user.getRoles()), user);
		}
	}
}

package com.gift.security;

import com.gift.model.entities.AuthenticationEntity;
import com.gift.model.projections.LocalUser;
import com.gift.repository.AuthenticationRepo;
import com.gift.util.CookieUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import static com.gift.security.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;
import static com.gift.security.HttpCookieOAuth2AuthorizationRequestRepository.SID_PARAM_COOKIE_NAME;

@Component
@Slf4j
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	private final AuthenticationRepo authenticationRepo;
	private TokenProvider tokenProvider;

	private HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

	@Autowired
	OAuth2AuthenticationSuccessHandler(TokenProvider tokenProvider,
									   AuthenticationRepo authenticationRepo, HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository) {
		this.tokenProvider = tokenProvider;
		this.authenticationRepo = authenticationRepo;
		this.httpCookieOAuth2AuthorizationRequestRepository = httpCookieOAuth2AuthorizationRequestRepository;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		String targetUrl = determineTargetUrl(request, response, authentication);
		Optional<String> sid = CookieUtils.getCookie(request, SID_PARAM_COOKIE_NAME).map(Cookie::getValue);
		String connectSid = sid.orElse(null);
		String uuid = String.valueOf(UUID.randomUUID());

		if (response.isCommitted()) {
			logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
			return;
		}

		clearAuthenticationAttributes(request, response);
		LocalUser userPrincipal = (LocalUser) authentication.getPrincipal();

		AuthenticationEntity authenticationEntity = authenticationRepo.findByUserId(userPrincipal.getUser().getId());

		if (authenticationEntity == null) {
			authenticationEntity = new AuthenticationEntity();
			authenticationEntity.setUserId(userPrincipal.getUser().getId());
		}

		authenticationEntity.setCode(uuid);
		authenticationEntity.setSid(connectSid);
		authenticationEntity.setToken(tokenProvider.createToken(authentication));

		authenticationRepo.save(authenticationEntity);

		getRedirectStrategy().sendRedirect(request, response, targetUrl);

	}

	@Override
	protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME).map(Cookie::getValue);
		Optional<String> sid = CookieUtils.getCookie(request, SID_PARAM_COOKIE_NAME).map(Cookie::getValue);

		String connectSid = sid.orElse(null);
		String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

		return UriComponentsBuilder.fromUriString(targetUrl).queryParam("sid", connectSid).build().toUriString();
	}

	protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
		super.clearAuthenticationAttributes(request);
		httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
	}
}

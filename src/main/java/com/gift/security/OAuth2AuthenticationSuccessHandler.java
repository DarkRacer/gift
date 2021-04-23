package com.gift.security;

import com.gift.model.projections.LocalUser;
import com.gift.util.CookieUtils;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.UserAuthResponse;
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

import static com.gift.security.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Component
@Slf4j
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private TokenProvider tokenProvider;


	private HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

	@Autowired
	OAuth2AuthenticationSuccessHandler(TokenProvider tokenProvider,
			HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository) {
		this.tokenProvider = tokenProvider;
		this.httpCookieOAuth2AuthorizationRequestRepository = httpCookieOAuth2AuthorizationRequestRepository;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		String targetUrl = determineTargetUrl(request, response, authentication);


		if (response.isCommitted()) {
			logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
			return;
		}

		clearAuthenticationAttributes(request, response);
		LocalUser userPrincipal = (LocalUser) authentication.getPrincipal();

		Cookie token = new Cookie("Authorization", tokenProvider.createToken(authentication));
		Cookie userId = new Cookie("user_id", String.valueOf(userPrincipal.getUser().getId()));

		token.setSecure(true);
		token.setMaxAge(180);
		token.setPath("/user");
		token.setHttpOnly(true);
		token.setDomain(".herokuapp.com");
		response.addCookie(token);

		userId.setSecure(true);
		userId.setMaxAge(360);
		userId.setPath("/user");
		userId.setDomain(".herokuapp.com");
		userId.setHttpOnly(true);
		response.addCookie(userId);

		response.addHeader("user_id", String.valueOf(userPrincipal.getUser().getId()));
		response.addHeader("Authorization", tokenProvider.createToken(authentication));
		log.info("user_id {}", userId.getValue());
		getRedirectStrategy().sendRedirect(request, response, targetUrl);

	}

	@Override
	protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME).map(Cookie::getValue);

		String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

		return UriComponentsBuilder.fromUriString(targetUrl).build().toUriString();
	}

	protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
		super.clearAuthenticationAttributes(request);
		httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
	}
}

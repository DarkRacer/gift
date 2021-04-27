package com.gift.controller;

import com.gift.model.entities.CurrentUser;
import com.gift.model.projections.AuthRequest;
import com.gift.model.projections.AuthResponse;
import com.gift.model.projections.LocalUser;
import com.gift.service.AuthenticationService;
import com.gift.util.GeneralUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class Auth {
    private final AuthenticationService authenticationService;

    @Autowired
    public Auth(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getCurrentUser(@CurrentUser LocalUser user) {
        return ResponseEntity.ok(GeneralUtils.buildUserInfo(user));
    }

    @PostMapping("/code")
    public AuthResponse getAuth (@RequestBody AuthRequest authRequest) {
        return authenticationService.getAuth(authRequest);
    }

    @PostMapping("/sid")
    public String getSid (HttpServletRequest httpServletRequest) {
        Cookie[] cookies = httpServletRequest.getCookies();
        for (Cookie cookie : cookies) {
            if (Objects.equals(cookie.getName(), "connect.sid")){
                return cookie.getValue();
            }
        }
        return null;
    }
}

package com.gift.service;

import com.gift.model.entities.AuthenticationEntity;
import com.gift.model.projections.AuthRequest;
import com.gift.model.projections.AuthResponse;
import com.gift.repository.AuthenticationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final AuthenticationRepo authenticationRepo;

    @Autowired
    public AuthenticationService(AuthenticationRepo authenticationRepo) {
        this.authenticationRepo = authenticationRepo;
    }

    public AuthResponse getAuth(AuthRequest authRequest) {
        AuthenticationEntity authenticationEntity = authenticationRepo.findByCodeAndSid(authRequest.getCode(),
                                                                                        authRequest.getSid());
        return new AuthResponse(authenticationEntity.getUserId(), authenticationEntity.getToken());
    }
}

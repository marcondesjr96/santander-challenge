package com.santander.challenge.transactions.application.command;

import com.santander.challenge.transactions.adapters.dto.AuthRequest;
import com.santander.challenge.transactions.adapters.dto.AuthResponse;
import com.santander.challenge.transactions.infrastructure.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticateUserUseCase {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthResponse execute(AuthRequest req) {
        try {
            var authToken = new UsernamePasswordAuthenticationToken(req.login(), req.password());
            authenticationManager.authenticate(authToken);
            String jwt = jwtService.generateToken(req.login());
            return new AuthResponse(jwt);
        } catch (AuthenticationException ex) {
            throw ex;
        }
    }
}

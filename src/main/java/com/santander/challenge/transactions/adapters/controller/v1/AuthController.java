package com.santander.challenge.transactions.adapters.controller.v1;

import com.santander.challenge.transactions.adapters.dto.AuthRequest;
import com.santander.challenge.transactions.adapters.dto.AuthResponse;
import com.santander.challenge.transactions.infrastructure.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse login(@RequestBody AuthRequest req) {
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

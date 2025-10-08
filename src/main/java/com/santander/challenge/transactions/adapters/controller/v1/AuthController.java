package com.santander.challenge.transactions.adapters.controller.v1;

import com.santander.challenge.transactions.adapters.dto.AuthRequest;
import com.santander.challenge.transactions.adapters.dto.AuthResponse;
import com.santander.challenge.transactions.application.command.AuthenticateUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticateUserUseCase authenticateUserUseCase;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse login(@RequestBody AuthRequest req) {
        return authenticateUserUseCase.execute(req);
    }
}

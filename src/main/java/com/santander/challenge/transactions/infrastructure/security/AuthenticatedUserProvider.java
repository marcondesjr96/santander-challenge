package com.santander.challenge.transactions.infrastructure.security;

import com.santander.challenge.transactions.domain.exception.UserNotFoundException;
import com.santander.challenge.transactions.domain.model.User;
import com.santander.challenge.transactions.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AuthenticatedUserProvider {

    private final UserRepository userRepository;

    public User getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String login = auth.getName();
        return userRepository.findByLogin(login)
                .orElseThrow(UserNotFoundException::new);
    }
}

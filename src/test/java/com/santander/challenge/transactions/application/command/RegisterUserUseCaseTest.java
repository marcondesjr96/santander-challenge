package com.santander.challenge.transactions.application.command;

import com.santander.challenge.transactions.adapters.dto.RegisterUserResponse;
import com.santander.challenge.transactions.domain.exception.*;
import com.santander.challenge.transactions.domain.model.Account;
import com.santander.challenge.transactions.domain.model.Cpf;
import com.santander.challenge.transactions.domain.model.User;
import com.santander.challenge.transactions.domain.repository.AccountRepository;
import com.santander.challenge.transactions.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterUserUseCaseTest {

    @Mock private UserRepository userRepository;
    @Mock private AccountRepository accountRepository;
    @Mock private PasswordEncoder passwordEncoder;

    @InjectMocks private RegisterUserUseCase registerUserUseCase;

    private User user;
    private Account account;

    @BeforeEach
    void setup() {
        user = new User(UUID.randomUUID(), "Marcondes Junior", new Cpf("49466331035"), "marcondes", "hashed");
        account = new Account(UUID.randomUUID(), user.getId(), BigDecimal.ZERO);
    }

    @Test
    void shouldRegisterUserSuccessfully() {
        when(userRepository.findByLogin("marcondes")).thenReturn(Optional.empty());
        when(userRepository.findByCpf("49466331035")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password")).thenReturn("hashed");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        RegisterUserResponse response = registerUserUseCase.execute("Marcondes Junior", "494.663.310-35", "marcondes", "password");

        assertThat(response).isNotNull();
        verify(userRepository).save(any(User.class));
        verify(accountRepository).save(any(Account.class));
    }

    @Test
    void shouldThrowWhenLoginAlreadyExists() {
        when(userRepository.findByLogin("marcondes")).thenReturn(Optional.of(user));

        assertThatThrownBy(() -> registerUserUseCase.execute("Marcondes Junior", "49466331035", "marcondes", "password"))
                .isInstanceOf(LoginAlreadyExistsException.class);
    }

    @Test
    void shouldThrowWhenCpfAlreadyRegistered() {
        when(userRepository.findByLogin("marcondes")).thenReturn(Optional.empty());
        when(userRepository.findByCpf("49466331035")).thenReturn(Optional.of(user));

        assertThatThrownBy(() -> registerUserUseCase.execute("Marcondes Junior", "49466331035", "marcondes", "password"))
                .isInstanceOf(CPFAlreadyRegisteredException.class);
    }

    @Test
    void shouldThrowWhenCpfInvalid() {
        assertThatThrownBy(() -> registerUserUseCase.execute("Marcondes Junior", "12345678900", "marcondes", "password"))
                .isInstanceOf(InvalidCpfException.class);
    }
}

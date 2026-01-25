package com.pdk.rentvideoapi.service;

import com.pdk.rentvideoapi.dto.auth.AuthResponse;
import com.pdk.rentvideoapi.dto.auth.LoginRequest;
import com.pdk.rentvideoapi.dto.auth.UserRegistrationRequest;
import com.pdk.rentvideoapi.entity.User;
import com.pdk.rentvideoapi.enums.UserRole;
import com.pdk.rentvideoapi.repository.UserRepository;
import com.pdk.rentvideoapi.security.JwtService;
import com.pdk.rentvideoapi.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.lang.management.RuntimeMXBean;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthServiceImpl authService;

    private User user;

    @BeforeEach
    void set_up() {
        user = User.builder()
                .id(1L)
                .email("test@example.com")
                .password("encodedPassword")
                .firstName("Test")
                .lastName("User")
                .role(UserRole.CUSTOMER)
                .build();

    }


//    registration with valid request
    @Test
    void register_success() {
        UserRegistrationRequest request = UserRegistrationRequest.builder()
                .email("test@example.com")
                .password("password")
                .firstName("Test")
                .lastName("User")
                .role(UserRole.CUSTOMER)
                .build();

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encoded-password");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(jwtService.generateToken(user)).thenReturn("jwt-token");

        AuthResponse response = authService.register(request);

        assertEquals("jwt-token", response.getToken());
        assertEquals("Bearer", response.getTokenType());

        verify(userRepository).existsByEmail(request.getEmail());
        verify(passwordEncoder).encode(request.getPassword());
        verify(jwtService).generateToken(user);
    }

//    Email already exists exception test
    @Test
    void register_emailAlreadyExists_shouldThrowException() {
        UserRegistrationRequest request = UserRegistrationRequest.builder()
                .email("test@example.com")
                .password("password")
                .build();

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> authService.register(request));

        assertEquals("Email already registered", exception.getMessage());

        verify(userRepository).existsByEmail(request.getEmail());
        verify(passwordEncoder, never()).encode(any());
        verify(jwtService, never()).generateToken(any());

    }

//    login with valid credentials
    @Test
    void login_success() {
        LoginRequest request = LoginRequest.builder()
                .email("test@example.com")
                .password("password")
                .build();

        when(userRepository.findByEmail(request.getEmail()))
                .thenReturn(Optional.of(user));
        when(passwordEncoder.matches(request.getPassword(), user.getPassword()))
                .thenReturn(true);
        when(jwtService.generateToken(user))
                .thenReturn("jwt-token");

        AuthResponse response = authService.login(request);

        assertNotNull(response);
        assertEquals("jwt-token", response.getToken());
        assertEquals("Bearer", response.getTokenType());

        verify(userRepository).findByEmail(request.getEmail());
        verify(passwordEncoder).matches(request.getPassword(), user.getPassword());
        verify(jwtService).generateToken(user);
    }

//    User not found exception test
    @Test
    void login_userNotFound_shouldThrowException() {
        LoginRequest request = LoginRequest.builder()
                .email("notfound@example.com")
                .password("password")
                .build();

        when(userRepository.findByEmail(request.getEmail()))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> authService.login(request)
        );

        assertEquals("Invalid credentials!", exception.getMessage());

        verify(userRepository).findByEmail(request.getEmail());
        verify(passwordEncoder, never()).matches(any(), any());
        verify(jwtService, never()).generateToken(any());
    }
//    login with invalid credentials
}

package com.pdk.rentvideoapi.service.impl;

import com.pdk.rentvideoapi.dto.auth.AuthResponse;
import com.pdk.rentvideoapi.dto.auth.LoginRequest;
import com.pdk.rentvideoapi.dto.auth.UserRegistrationRequest;
import com.pdk.rentvideoapi.entity.User;
import com.pdk.rentvideoapi.enums.UserRole;
import com.pdk.rentvideoapi.repository.UserRepository;
import com.pdk.rentvideoapi.security.JwtService;
import com.pdk.rentvideoapi.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public AuthResponse register(UserRegistrationRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .role(request.getRole() != null ? request.getRole() : UserRole.CUSTOMER)
                .build();

        User savedUser = userRepository.save(user);

        String token = jwtService.generateToken(savedUser);

        return AuthResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials!"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials!");
        }

        String token = jwtService.generateToken(user);

        return AuthResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .build();
    }
}

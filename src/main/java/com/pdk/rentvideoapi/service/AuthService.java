package com.pdk.rentvideoapi.service;

import com.pdk.rentvideoapi.dto.auth.AuthResponse;
import com.pdk.rentvideoapi.dto.auth.LoginRequest;
import com.pdk.rentvideoapi.dto.auth.UserRegistrationRequest;

public interface AuthService {
    AuthResponse register(UserRegistrationRequest request);

    AuthResponse login(LoginRequest request);
}

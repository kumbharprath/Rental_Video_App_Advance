package com.pdk.rentvideoapi.controller;

import jakarta.validation.Valid;
import com.pdk.rentvideoapi.controller.endpoints.ApiEndpoints;
import com.pdk.rentvideoapi.dto.auth.AuthResponse;
import com.pdk.rentvideoapi.dto.auth.LoginRequest;
import com.pdk.rentvideoapi.dto.auth.UserRegistrationRequest;
import com.pdk.rentvideoapi.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping(ApiEndpoints.RENTVIDEOAPP_API_ENDPOINT)
@Tag(name = "Authentication", description = "Endpoints for user registration and login")
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "Register a new user",
            description = "Creates a new user account and returns an authentication token",
            responses = {
                    @ApiResponse(responseCode = "201", description = "User registered successfully",
                            content = @Content(schema = @Schema(implementation = AuthResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input data")
            }
    )
    @PostMapping(ApiEndpoints.USER_REGISTER_API_ENDPOINT)
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody UserRegistrationRequest request) {
        AuthResponse authResponse = authService.register(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authResponse);
    }

    @PostMapping(ApiEndpoints.USER_LOGIN_API_ENDPOINT)
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

}

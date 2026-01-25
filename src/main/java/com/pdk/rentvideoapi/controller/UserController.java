package com.pdk.rentvideoapi.controller;

import com.pdk.rentvideoapi.controller.endpoints.ApiEndpoints;
import com.pdk.rentvideoapi.dto.auth.UserRegistrationRequest;
import com.pdk.rentvideoapi.dto.user.UserResponse;
import com.pdk.rentvideoapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiEndpoints.RENTVIDEOAPP_API_ENDPOINT)
public class UserController {

    private final UserService userService;

    @GetMapping("users/me")
    public UserResponse getCurrentUser() {
        return userService.getCurrentUser();
    }
}

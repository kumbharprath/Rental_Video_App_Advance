package com.pdk.rentvideoapi.service;


import com.pdk.rentvideoapi.dto.auth.UserRegistrationRequest;
import com.pdk.rentvideoapi.dto.user.UserResponse;


public interface UserService {

    UserResponse getCurrentUser();
}


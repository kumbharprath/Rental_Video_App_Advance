package com.pdk.rentvideoapi.mapper;

import com.pdk.rentvideoapi.dto.user.UserResponse;
import com.pdk.rentvideoapi.entity.User;

public class UserMapper {

    public static UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole())
                .build();
    }
}

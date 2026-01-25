package com.pdk.rentvideoapi.dto.auth;

import com.pdk.rentvideoapi.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserRegistrationRequest {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private UserRole role;
}

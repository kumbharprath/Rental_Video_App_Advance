package com.pdk.rentvideoapi.dto.user;

import com.pdk.rentvideoapi.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserResponse {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private UserRole role;
}

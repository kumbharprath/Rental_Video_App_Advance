package com.pdk.rentvideoapi.service.impl;

import com.pdk.rentvideoapi.entity.User;
import com.pdk.rentvideoapi.mapper.UserMapper;
import com.pdk.rentvideoapi.repository.UserRepository;
import com.pdk.rentvideoapi.security.SecurityUtil;
import com.pdk.rentvideoapi.service.UserService;
import com.pdk.rentvideoapi.dto.user.UserResponse;
import com.pdk.rentvideoapi.dto.auth.UserRegistrationRequest;
import com.pdk.rentvideoapi.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;


    @Override
    public UserResponse getCurrentUser() {

        String email = SecurityUtil.getCurrentUserEmail();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found!"));


        return UserMapper.toResponse(user);
    }

}

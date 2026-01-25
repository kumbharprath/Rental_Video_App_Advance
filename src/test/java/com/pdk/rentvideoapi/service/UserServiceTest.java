package com.pdk.rentvideoapi.service;

import com.pdk.rentvideoapi.dto.user.UserResponse;
import com.pdk.rentvideoapi.entity.User;
import com.pdk.rentvideoapi.enums.UserRole;
import com.pdk.rentvideoapi.repository.UserRepository;
import com.pdk.rentvideoapi.security.SecurityUtil;
import com.pdk.rentvideoapi.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .email("test@example.com")
                .firstName("Test")
                .lastName("User")
                .role(UserRole.CUSTOMER)
                .build();
    }

    @Test
    void getCurrentUser_successfull() {
        try(MockedStatic<SecurityUtil> mockedSecurityUtil =
                mockStatic(SecurityUtil.class)) {

            mockedSecurityUtil.when(SecurityUtil::getCurrentUserEmail)
                    .thenReturn("test@example.com");

            when(userRepository.findByEmail("test@example.com"))
                    .thenReturn(Optional.of(user));

            UserResponse response = userService.getCurrentUser();

            assertNotNull(response);
            assertEquals("test@example.com", response.getEmail());
            assertEquals("Test", response.getFirstName());
            assertEquals("User", response.getLastName());

            verify(userRepository).findByEmail("test@example.com");
        }
    }

    @Test
    void getCurrentUser_userNotFound_shouldThrowException() {

        try (MockedStatic<SecurityUtil> mockedSecurityUtil =
                     mockStatic(SecurityUtil.class)) {

            mockedSecurityUtil
                    .when(SecurityUtil::getCurrentUserEmail)
                    .thenReturn("missing@example.com");

            when(userRepository.findByEmail("missing@example.com"))
                    .thenReturn(Optional.empty());

            RuntimeException exception = assertThrows(
                    RuntimeException.class,
                    () -> userService.getCurrentUser()
            );

            assertEquals("User not found!", exception.getMessage());

            verify(userRepository).findByEmail("missing@example.com");
        }
    }
}

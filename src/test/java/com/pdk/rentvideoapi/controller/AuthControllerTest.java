package com.pdk.rentvideoapi.controller;

import com.pdk.rentvideoapi.controller.endpoints.ApiEndpoints;
import com.pdk.rentvideoapi.dto.auth.AuthResponse;
import com.pdk.rentvideoapi.dto.auth.LoginRequest;
import com.pdk.rentvideoapi.dto.auth.UserRegistrationRequest;
import com.pdk.rentvideoapi.enums.UserRole;
import com.pdk.rentvideoapi.security.CustomUserDetailsService;
import com.pdk.rentvideoapi.security.JwtService;
import com.pdk.rentvideoapi.service.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthService authService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void register_shouldReturnCreatedStatus() throws Exception {

        UserRegistrationRequest request = UserRegistrationRequest.builder()
                .email("test@example.com")
                .password("password123")
                .firstName("Test")
                .lastName("User")
                .role(UserRole.CUSTOMER)
                .build();

        AuthResponse response = AuthResponse.builder()
                .token("access-token")
                .tokenType("Bearer")
                .build();

        Mockito.when(authService.register(Mockito.any(UserRegistrationRequest.class))).thenReturn(response);

        mockMvc.perform(post(ApiEndpoints.RENTVIDEOAPP_API_ENDPOINT
                + ApiEndpoints.USER_REGISTER_API_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token").value("access-token"))
                .andExpect(jsonPath("$.tokenType").value("Bearer"));
    }

    @Test
    void login() throws Exception {

        LoginRequest request = LoginRequest.builder()
                .email("test@example.com")
                .password("password123")
                .build();

        AuthResponse response = AuthResponse.builder()
                .token("access-token")
                .tokenType("Bearer")
                .build();

        Mockito.when(authService.login(Mockito.any(LoginRequest.class))).thenReturn(response);

        mockMvc.perform(post(ApiEndpoints.RENTVIDEOAPP_API_ENDPOINT
                        + ApiEndpoints.USER_LOGIN_API_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("access-token"))
                .andExpect(jsonPath("$.tokenType").value("Bearer"));
    }
}

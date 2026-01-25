package com.pdk.rentvideoapi.service;

import com.pdk.rentvideoapi.dto.rental.RentalResponse;
import com.pdk.rentvideoapi.entity.Rental;
import com.pdk.rentvideoapi.entity.User;
import com.pdk.rentvideoapi.entity.Video;
import com.pdk.rentvideoapi.enums.RentalStatus;
import com.pdk.rentvideoapi.enums.UserRole;
import com.pdk.rentvideoapi.repository.RentalRepository;
import com.pdk.rentvideoapi.repository.UserRepository;
import com.pdk.rentvideoapi.repository.VideoRepository;
import com.pdk.rentvideoapi.security.SecurityUtil;
import com.pdk.rentvideoapi.service.impl.RentalServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RentalServiceTest {

    @Mock
    private RentalRepository rentalRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private VideoRepository videoRepository;

    @InjectMocks
    private RentalServiceImpl rentalService;

    private User user;
    private Video video;
    private Rental rental;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .email("test@example.com")
                .role(UserRole.CUSTOMER)
                .build();

        video = Video.builder()
                .id(10L)
                .title("Inception")
                .build();

        rental = Rental.builder()
                .id(100L)
                .user(user)
                .video(video)
                .status(RentalStatus.RENTED)
                .rentedAt(LocalDateTime.now())
                .build();
    }

    // =========================
    // rentVideo()
    // =========================

    @Test
    void rentVideo_success() {

        try (MockedStatic<SecurityUtil> mockedSecurity =
                     mockStatic(SecurityUtil.class)) {

            mockedSecurity
                    .when(SecurityUtil::getCurrentUserEmail)
                    .thenReturn("test@example.com");

            when(userRepository.findByEmail("test@example.com"))
                    .thenReturn(Optional.of(user));
            when(videoRepository.findById(10L))
                    .thenReturn(Optional.of(video));
            when(rentalRepository.countByUserAndReturnedAtIsNull(user))
                    .thenReturn(1L);
            when(rentalRepository.save(any(Rental.class)))
                    .thenReturn(rental);

            RentalResponse response = rentalService.rentVideo(10L);

            assertNotNull(response);
            assertEquals(RentalStatus.RENTED.name(), response.getStatus());

            verify(rentalRepository).save(any(Rental.class));
        }
    }

    @Test
    void rentVideo_moreThanTwoActiveRentals_shouldThrowException() {

        try (MockedStatic<SecurityUtil> mockedSecurity =
                     mockStatic(SecurityUtil.class)) {

            mockedSecurity
                    .when(SecurityUtil::getCurrentUserEmail)
                    .thenReturn("test@example.com");

            when(userRepository.findByEmail("test@example.com"))
                    .thenReturn(Optional.of(user));
            when(videoRepository.findById(10L))
                    .thenReturn(Optional.of(video));
            when(rentalRepository.countByUserAndReturnedAtIsNull(user))
                    .thenReturn(3L);

            RuntimeException exception = assertThrows(
                    RuntimeException.class,
                    () -> rentalService.rentVideo(10L)
            );

            assertEquals("Maximum 2 active rentals allowed", exception.getMessage());

            verify(rentalRepository, never()).save(any());
        }
    }

    @Test
    void rentVideo_videoNotFound_shouldThrowException() {

        try (MockedStatic<SecurityUtil> mockedSecurity =
                     mockStatic(SecurityUtil.class)) {

            mockedSecurity
                    .when(SecurityUtil::getCurrentUserEmail)
                    .thenReturn("test@example.com");

            when(userRepository.findByEmail("test@example.com"))
                    .thenReturn(Optional.of(user));
            when(videoRepository.findById(10L))
                    .thenReturn(Optional.empty());

            RuntimeException exception = assertThrows(
                    RuntimeException.class,
                    () -> rentalService.rentVideo(10L)
            );

            assertEquals("Video with ID 10 not found!", exception.getMessage());
        }
    }

    // =========================
    // returnVideo()
    // =========================

    @Test
    void returnVideo_success() {

        try (MockedStatic<SecurityUtil> mockedSecurity =
                     mockStatic(SecurityUtil.class)) {

            mockedSecurity
                    .when(SecurityUtil::getCurrentUserEmail)
                    .thenReturn("test@example.com");

            when(userRepository.findByEmail("test@example.com"))
                    .thenReturn(Optional.of(user));
            when(videoRepository.findById(10L))
                    .thenReturn(Optional.of(video));
            when(rentalRepository.findByUserAndVideoAndReturnedAtIsNull(user, video))
                    .thenReturn(Optional.of(rental));
            when(rentalRepository.save(any(Rental.class)))
                    .thenReturn(rental);

            RentalResponse response = rentalService.returnVideo(10L);

            assertNotNull(response);
            assertEquals(RentalStatus.RETURNED.name(), response.getStatus());
            assertNotNull(response.getReturnedAt());
        }
    }

    @Test
    void returnVideo_activeRentalNotFound_shouldThrowException() {

        try (MockedStatic<SecurityUtil> mockedSecurity =
                     mockStatic(SecurityUtil.class)) {

            mockedSecurity
                    .when(SecurityUtil::getCurrentUserEmail)
                    .thenReturn("test@example.com");

            when(userRepository.findByEmail("test@example.com"))
                    .thenReturn(Optional.of(user));
            when(videoRepository.findById(10L))
                    .thenReturn(Optional.of(video));
            when(rentalRepository.findByUserAndVideoAndReturnedAtIsNull(user, video))
                    .thenReturn(Optional.empty());

            RuntimeException exception = assertThrows(
                    RuntimeException.class,
                    () -> rentalService.returnVideo(10L)
            );

            assertEquals("Active rental not found", exception.getMessage());
        }
    }
}

// ======================

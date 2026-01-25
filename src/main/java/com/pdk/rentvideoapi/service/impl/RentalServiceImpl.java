package com.pdk.rentvideoapi.service.impl;

import com.pdk.rentvideoapi.dto.rental.RentalResponse;
import com.pdk.rentvideoapi.entity.Rental;
import com.pdk.rentvideoapi.entity.User;
import com.pdk.rentvideoapi.entity.Video;
import com.pdk.rentvideoapi.enums.RentalStatus;
import com.pdk.rentvideoapi.mapper.RentalMapper;
import com.pdk.rentvideoapi.repository.RentalRepository;
import com.pdk.rentvideoapi.repository.UserRepository;
import com.pdk.rentvideoapi.repository.VideoRepository;
import com.pdk.rentvideoapi.security.SecurityUtil;
import com.pdk.rentvideoapi.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {

    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;
    private final VideoRepository videoRepository;

    @Override
    public RentalResponse rentVideo(Long videoId) {
        User user = getUser();
        Video video = getVideo(videoId);

        long activeRentals = rentalRepository.countByUserAndReturnedAtIsNull(user);

        if(activeRentals > 2)
            throw new RuntimeException("Maximum 2 active rentals allowed");

        Rental rental = Rental.builder()
                .user(user)
                .video(video)
                .status(RentalStatus.RENTED)
                .rentedAt(LocalDateTime.now())
                .build();

        rentalRepository.save(rental);

        return RentalMapper.mapToResponse(rental);
    }

    @Override
    public RentalResponse returnVideo(Long videoId) {
        User user = getUser();
        Video video = getVideo(videoId);

        Rental existedRental = rentalRepository
                .findByUserAndVideoAndReturnedAtIsNull(user, video)
                .orElseThrow(() -> new RuntimeException("Active rental not found"));

        existedRental.setReturnedAt(LocalDateTime.now());
        existedRental.setStatus(RentalStatus.RETURNED);

        Rental updatedRental = rentalRepository.save(existedRental);

        return RentalMapper.mapToResponse(updatedRental);
    }

    @Override
    public List<RentalResponse> getMyRentals() {
        return rentalRepository.findByUser(getUser())
                .stream()
                .map(RentalMapper::mapToResponse)
                .toList();
    }

    private User getUser() {
        String email = SecurityUtil.getCurrentUserEmail();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found!"));
    }

    private Video getVideo(long videoId) {
        return videoRepository.findById(videoId)
                .orElseThrow(() -> new RuntimeException("Video with ID " + videoId + " not found!"));
    }
}

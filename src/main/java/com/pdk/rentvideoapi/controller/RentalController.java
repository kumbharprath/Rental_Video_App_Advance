package com.pdk.rentvideoapi.controller;

import com.pdk.rentvideoapi.controller.endpoints.ApiEndpoints;
import com.pdk.rentvideoapi.dto.rental.RentalResponse;
import com.pdk.rentvideoapi.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiEndpoints.RENTVIDEOAPP_API_ENDPOINT)
public class RentalController {

    private final RentalService rentalService;

    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    @PostMapping(ApiEndpoints.RENT_VIDEO_API_ENDPOINT)
    public ResponseEntity<RentalResponse> rentVideo(
            @PathVariable Long videoId
    ) {
        return ResponseEntity.ok(rentalService.rentVideo(videoId));
    }

    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    @PostMapping(ApiEndpoints.RETURN_VIDEO_API_ENDPOINT)
    public ResponseEntity<RentalResponse> returnVideo(
            @PathVariable Long videoId
    ) {
        return ResponseEntity.ok(rentalService.returnVideo(videoId));
    }

    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    @GetMapping("/rentals/me")
    public List<RentalResponse> getMyRentals() {
        return rentalService.getMyRentals();
    }
}

package com.pdk.rentvideoapi.mapper;

import com.pdk.rentvideoapi.dto.rental.RentalResponse;
import com.pdk.rentvideoapi.entity.Rental;

public class RentalMapper {

    public static RentalResponse mapToResponse(Rental rental) {
        return RentalResponse.builder()
                .rentalId(rental.getId())
                .videoId(rental.getVideo().getId())
                .videoTitle(rental.getVideo().getTitle())
                .rentedAt(rental.getRentedAt())
                .returnedAt(rental.getReturnedAt())
                .status(rental.getStatus().name())
                .build();
    }
}

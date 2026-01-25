package com.pdk.rentvideoapi.service;


import com.pdk.rentvideoapi.dto.rental.RentalResponse;

import java.util.List;

public interface RentalService {

    RentalResponse rentVideo(Long videoId);

    RentalResponse returnVideo(Long videoId);

    List<RentalResponse> getMyRentals();
}


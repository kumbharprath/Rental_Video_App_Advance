package com.pdk.rentvideoapi.dto.rental;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RentalResponse {

    private Long rentalId;
    private Long videoId;
    private String videoTitle;
    private LocalDateTime rentedAt;
    private LocalDateTime returnedAt;
    private String status;
}


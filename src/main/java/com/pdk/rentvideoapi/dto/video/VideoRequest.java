package com.pdk.rentvideoapi.dto.video;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@Builder
public class VideoRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String director;

    @NotBlank
    private String genre;
}

package com.pdk.rentvideoapi.mapper;

import com.pdk.rentvideoapi.dto.video.VideoRequest;
import com.pdk.rentvideoapi.dto.video.VideoResponse;
import com.pdk.rentvideoapi.entity.Video;

public class VideoMapper {
    public static VideoResponse mapToResponse(Video video) {
        return VideoResponse.builder()
                .id(video.getId())
                .title(video.getTitle())
                .director(video.getDirector())
                .genre(video.getGenre())
                .available(video.getIsAvailable())
                .build();
    }

    public static Video mapToEntity(VideoRequest videoRequest) {
        return Video.builder()
                .title(videoRequest.getTitle())
                .director(videoRequest.getDirector())
                .genre(videoRequest.getGenre())
                .isAvailable(true)
                .build();
    }

    public static void updateEntity(VideoRequest request, Video video) {
        if(request == null || video == null) {
            return;
        }

        video.setTitle(request.getTitle());
        video.setDirector(request.getDirector());
        video.setGenre(request.getGenre());
    }
}

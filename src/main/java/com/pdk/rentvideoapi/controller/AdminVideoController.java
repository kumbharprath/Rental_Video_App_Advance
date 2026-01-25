package com.pdk.rentvideoapi.controller;

import com.pdk.rentvideoapi.controller.endpoints.ApiEndpoints;
import com.pdk.rentvideoapi.dto.video.VideoRequest;
import com.pdk.rentvideoapi.dto.video.VideoResponse;
import com.pdk.rentvideoapi.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiEndpoints.RENTVIDEOAPP_API_ENDPOINT)
public class AdminVideoController {

    private final VideoService videoService;

    //    Public endpoint
    @GetMapping(ApiEndpoints.VIDEO_API_ENDPOINT)
    public ResponseEntity<List<VideoResponse>> getAllVideos() {
        return ResponseEntity.ok(videoService.getAll());
    }

    //    Admin only endpoints
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(ApiEndpoints.VIDEO_ADMIN_API_ENDPOINT)
    public ResponseEntity<VideoResponse> create(@RequestBody VideoRequest request) {
        return new ResponseEntity<>(videoService.create(request), HttpStatus.CREATED);
    }

    //    Admin only endpoints
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(ApiEndpoints.VIDEO_ADMIN_API_ENDPOINT + "/{id}")
    public VideoResponse update(@PathVariable Long id,
                                @RequestBody VideoRequest request) {
        return videoService.update(id, request);
    }

    //    Admin only endpoints
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(ApiEndpoints.VIDEO_ADMIN_API_ENDPOINT + "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        videoService.delete(id);
    }
}

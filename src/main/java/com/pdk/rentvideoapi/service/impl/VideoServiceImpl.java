package com.pdk.rentvideoapi.service.impl;

import com.pdk.rentvideoapi.dto.video.VideoRequest;
import com.pdk.rentvideoapi.dto.video.VideoResponse;
import com.pdk.rentvideoapi.entity.Video;
import com.pdk.rentvideoapi.mapper.VideoMapper;
import com.pdk.rentvideoapi.repository.VideoRepository;
import com.pdk.rentvideoapi.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {
    public final VideoRepository videoRepository;

    @Override
    public VideoResponse create(VideoRequest request) {
        Video video = VideoMapper.mapToEntity(request);

        Video savedVideo = videoRepository.save(video);
        return VideoMapper.mapToResponse(savedVideo);
    }

    @Override
    public VideoResponse update(Long id, VideoRequest request) {
        Video existingVideo = videoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Video not found"));

        VideoMapper.updateEntity(request, existingVideo);

        return VideoMapper.mapToResponse(videoRepository.save(existingVideo));
    }

    @Override
    public void delete(Long id) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Video not found"));

        videoRepository.delete(video);
    }

    @Override
    public List<VideoResponse> getAll() {
        return videoRepository.findAll()
                .stream()
                .map(VideoMapper::mapToResponse)
                .toList();
    }
}

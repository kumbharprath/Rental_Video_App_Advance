package com.pdk.rentvideoapi.repository;

import com.pdk.rentvideoapi.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video, Long> {
}

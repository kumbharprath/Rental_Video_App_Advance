package com.pdk.rentvideoapi.entity;


import com.pdk.rentvideoapi.enums.RentalStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "rentals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id", nullable = false)
    private Video video;

    @Column(name = "rented_at", nullable = false)
    private LocalDateTime rentedAt;

    @Column(name = "returned_at")
    private LocalDateTime returnedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RentalStatus status;
}

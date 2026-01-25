package com.pdk.rentvideoapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "videos")
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String director;

    @Column(nullable = false)
    private String genre;

    @Column(name = "availability")
    private boolean isAvailable = true;

    @OneToMany(
            mappedBy = "video",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Rental> rentals;

    public boolean getIsAvailable() {
        return this.isAvailable;
    }
}

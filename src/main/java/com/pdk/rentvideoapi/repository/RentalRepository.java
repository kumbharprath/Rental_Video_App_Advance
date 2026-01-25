package com.pdk.rentvideoapi.repository;

import com.pdk.rentvideoapi.entity.Rental;
import com.pdk.rentvideoapi.entity.User;
import com.pdk.rentvideoapi.entity.Video;
import com.pdk.rentvideoapi.enums.RentalStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RentalRepository extends JpaRepository<Rental, Long> {
//   This method is used to Enforce user can have Max 2 Active Rentals
    long countByUserAndReturnedAtIsNull(User user);

//    To find the rental record of user
    List<Rental> findByUser(User user);

//    To check if user is renting the video that is already rented.
    Optional<Rental> findByUserAndVideoAndReturnedAtIsNull(User user, Video video);

    List<Rental> findByUserAndStatus(User user, RentalStatus status);
}

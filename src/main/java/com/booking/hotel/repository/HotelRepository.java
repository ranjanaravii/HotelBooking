package com.booking.hotel.repository;

import com.booking.hotel.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findByCityIdAndDeletedFalse(Long cityId);
}

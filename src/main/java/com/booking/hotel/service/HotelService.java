package com.booking.hotel.service;

import com.booking.hotel.model.Hotel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface HotelService {
  List<Hotel> getAllHotels();

  List<Hotel> getHotelsByCity(Long cityId);

  Hotel createNewHotel(Hotel hotel);

  ResponseEntity<Hotel> getHotelById(Long id);

  void deleteHotelById(Long id);

  List<Hotel> getTop3ClosestHotels(Long cityId);
}

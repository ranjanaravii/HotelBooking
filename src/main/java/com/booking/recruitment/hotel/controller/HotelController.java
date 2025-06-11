package com.booking.recruitment.hotel.controller;

import com.booking.recruitment.hotel.common.Constants;
import com.booking.recruitment.hotel.model.Hotel;
import com.booking.recruitment.hotel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotel")
public class HotelController {
  private final HotelService hotelService;

  @Autowired
  public HotelController(HotelService hotelService) {
    this.hotelService = hotelService;
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<Hotel> getAllHotels() {
    return hotelService.getAllHotels();
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Hotel createHotel(@RequestBody Hotel hotel) {
    return hotelService.createNewHotel(hotel);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Hotel> getHotelById(@PathVariable Long id) {
    return hotelService.getHotelById(id);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteHotel(@PathVariable Long id) {
    hotelService.deleteHotelById(id);
  }

  @GetMapping("/search/{cityId}")
  public ResponseEntity<List<Hotel>> searchHotelsByDistance(
          @PathVariable Long cityId,
          @RequestParam(name = "sortBy", required = false) String sortBy) {
    if (Constants.DISTANCE.equalsIgnoreCase(sortBy)) {
      List<Hotel> topHotels = hotelService.getTop3ClosestHotels(cityId);
      return ResponseEntity.ok(topHotels);
    } else {
      return ResponseEntity.badRequest().build();
    }
  }
}

package com.booking.hotel.service.impl;

import com.booking.hotel.exception.BadRequestException;
import com.booking.hotel.exception.ElementNotFoundException;
import com.booking.hotel.model.City;
import com.booking.hotel.model.Hotel;
import com.booking.hotel.repository.HotelRepository;
import com.booking.hotel.service.CityService;
import com.booking.hotel.service.HotelService;
import com.booking.hotel.utility.DistanceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
class DefaultHotelService implements HotelService {
  private final HotelRepository hotelRepository;

  private final CityService cityService;

  @Autowired
  DefaultHotelService(HotelRepository hotelRepository, CityService cityService) {
    this.hotelRepository = hotelRepository;
      this.cityService = cityService;
  }

  @Override
  public List<Hotel> getAllHotels() {
    return hotelRepository.findAll();
  }

  @Override
  public List<Hotel> getHotelsByCity(Long cityId) {
    return hotelRepository.findAll().stream()
        .filter((hotel) -> cityId.equals(hotel.getCity().getId()))
        .collect(Collectors.toList());
  }

  @Override
  public Hotel createNewHotel(Hotel hotel) {
    if (hotel.getId() != null) {
      throw new BadRequestException("The ID must not be provided when creating a new Hotel");
    }

    return hotelRepository.save(hotel);
  }

  @Override
  public ResponseEntity<Hotel> getHotelById(Long id) {
    try {
      Hotel hotel = hotelRepository.findById(id)
              .orElseThrow(() -> new ElementNotFoundException("Hotel not found with ID: " + id));
      return ResponseEntity.ok(hotel);
    } catch (ElementNotFoundException ex) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    } catch (Exception ex) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

  public void deleteHotelById(Long id) {
    Hotel hotel = hotelRepository.findById(id)
            .orElseThrow(() -> new ElementNotFoundException("Hotel not found with id: " + id));
    hotel.setDeleted(true);
    hotelRepository.save(hotel);
  }

  public List<Hotel> getTop3ClosestHotels(Long cityId) {
    City city = cityService.getCityById(cityId);
    //Exclude logically deleted
    List<Hotel> hotels = hotelRepository.findByCityIdAndDeletedFalse(cityId);
    return hotels.stream()
            .sorted(Comparator.comparingDouble(hotel ->
                    DistanceUtil.calculateDistance(
                            hotel.getLatitude(), hotel.getLongitude(),
                            city.getCityCentreLatitude(), city.getCityCentreLongitude())))
            .limit(3)
            .collect(Collectors.toList());
  }

}

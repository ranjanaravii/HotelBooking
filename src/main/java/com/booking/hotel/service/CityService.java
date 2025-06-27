package com.booking.hotel.service;

import com.booking.hotel.model.City;

import java.util.List;

public interface CityService {
  List<City> getAllCities();

  City getCityById(Long id);

  City createCity(City city);
}

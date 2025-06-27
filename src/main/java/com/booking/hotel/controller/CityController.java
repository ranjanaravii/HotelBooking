package com.booking.hotel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.booking.hotel.exception.BadRequestException;
import com.booking.hotel.exception.ElementNotFoundException;
import com.booking.hotel.model.City;
import com.booking.hotel.service.CityService;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/city")
public class CityController {
  private final CityService cityService;

  @Autowired
  public CityController(CityService cityService) {
    this.cityService = cityService;
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<City> getAllCities() {
    return cityService.getAllCities();
  }

  @GetMapping("/{id}")
  public ResponseEntity<City> getCityById(@PathVariable Long id) {
    try {
      City city = cityService.getCityById(id);
      return ResponseEntity.ok(city);
    } catch (ElementNotFoundException ex) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<City> createCity(@Valid @RequestBody City city) {
    try {
      City createdCity = cityService.createCity(city);
      return ResponseEntity.status(HttpStatus.CREATED).body(createdCity);
    } catch (BadRequestException ex) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
  }
}

package com.booking.hotel.service;

import com.booking.hotel.dto.RatingReportDto;
import com.booking.hotel.model.Hotel;

import java.util.List;

public interface RatingService {
    RatingReportDto getRatingAverage(Long cityId);

    RatingReportDto getRatingAverage(List<Hotel> hotels);
}

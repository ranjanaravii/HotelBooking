package com.booking.recruitment.hotel.service.impl;

import com.booking.recruitment.hotel.model.City;
import com.booking.recruitment.hotel.model.Hotel;
import com.booking.recruitment.hotel.repository.HotelRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HotelServiceTest {

    @Mock
    private HotelRepository hotelRepository;
    @InjectMocks
    private DefaultHotelService hotelService;

    @Mock
    private DefaultCityService cityService;

    @Test
    @DisplayName("GetHotelById")
    void getHotelById() {
        City city = new City(10L, "Gurgaon", 10.4, 11.5);
        Hotel hotel = new Hotel(1L, "Test Hotel", 9.0, city, "Gurgaon", 10.4, 11.5, false);
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));

        Hotel result = hotelService.getHotelById(1L).getBody();

        assertNotNull(result);
        assertEquals("Test Hotel", result.getName());
    }

    @Test
    @DisplayName("DeleteHotelById")
    void deleteHotelById() {
        City city = new City(10L, "Gurgaon", 10.4, 11.5);
        Hotel hotel = new Hotel(1L, "To Be Deleted", 9.0, city, "Gurgaon", 10.4, 11.5, false);
        when(hotelRepository.findById(2L)).thenReturn(Optional.of(hotel));

        hotelService.deleteHotelById(2L);

        assertTrue(hotel.isDeleted());
        verify(hotelRepository).save(hotel);
    }

    @Test
    @DisplayName("GetTop3ClosestHotels")
    void getTop3ClosestHotels() {
        City city = new City(1L, "Sample City", 12.0, 77.0);
        when(cityService.getCityById(1L)).thenReturn(city);

        List<Hotel> hotels = Arrays.asList(
                new Hotel(1L, "Hotel A", 8.0, city, "A", 12.01, 77.01, false),
                new Hotel(2L, "Hotel B", 9.0, city, "B", 12.03, 77.04, false),
                new Hotel(3L, "Hotel C", 7.0, city, "C", 12.00, 77.00, false),
                new Hotel(4L, "Hotel D", 6.5, city, "D", 12.02, 77.03, false)
        );
        when(hotelRepository.findByCityIdAndDeletedFalse(1L)).thenReturn(hotels);

        List<Hotel> top3 = hotelService.getTop3ClosestHotels(1L);

        assertEquals(3, top3.size());
        assertEquals("Hotel C", top3.get(0).getName());
    }


}

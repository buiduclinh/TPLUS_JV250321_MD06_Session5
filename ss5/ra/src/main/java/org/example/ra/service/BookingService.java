package org.example.ra.service;

import org.example.ra.model.Booking;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.Map;

public interface BookingService {
     Booking findById(Long id);
     Booking create(Long userId, Long showtimeId, Booking booking);
     Booking cancel(Long id);
     Page<Map<String,Object>> findAllMoviesRevenue(int page, int size, LocalDateTime startDate, LocalDateTime endDate);
     Page<Map<String,Object>> findAllCinemasRevenue(int page, int size, LocalDateTime startDate, LocalDateTime endDate);
}

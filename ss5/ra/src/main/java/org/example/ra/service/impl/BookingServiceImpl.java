package org.example.ra.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.example.ra.model.Booking;
import org.example.ra.model.Showtime;
import org.example.ra.repo.BookingRepository;
import org.example.ra.service.BookingService;
import org.example.ra.service.ShowtimeService;
import org.example.ra.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.example.ra.model.Status.CANCELLED;

@Slf4j
@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ShowtimeService showtimeService;

    public Booking findById(Long id) {
        return bookingRepository.findById(id).orElseThrow(() ->
        {
            log.error("Failed to find cinema [{}]", id);
            return new EntityNotFoundException("Booking not found with id " + id);
        });
    }

    public Booking create(Long userId, Long showtimeId, Booking booking) {
        Showtime showtime = showtimeService.getById(showtimeId);
        int availableSeats = showtime.getTotalSeat() - showtime.getSeatBooked();
        try {
            if (availableSeats <= 0) {
                log.warn("There are no available Seats");
                throw new IllegalStateException("There are no available Seats");
            }
            if (booking.getSeats() > availableSeats) {
                log.error("Your Seats is larger than available Seats");
                throw new IllegalArgumentException("Your Seats is larger than available Seats");
            }
            int affectedSeats = showtime.getSeatBooked() + booking.getSeats();
            if(affectedSeats > showtime.getTotalSeat()) {
                log.warn("Your Seats is larger than available Seats");
                throw new Exception("Your Seats is larger than available Seats");
            }
            showtime.setSeatBooked(affectedSeats);
            booking.setUser(userService.getUserId(userId));
            booking.setShowtime(showtime);
            log.info("Creating cinema [{}]", booking);
            return bookingRepository.save(booking);
        } catch (Exception e) {
            log.error("Failed to create cinema [{}]", userId, e);
            throw new RuntimeException(e);
        }
    }

    public Booking cancel(Long id) {
        Booking booking = findById(id);
        if (booking == null) {
            log.error("Booking not found with id [{}]", id);
            throw new EntityNotFoundException("Booking not found with id " + id);
        }
        Showtime showtime = showtimeService.getById(booking.getShowtime().getId());
        if (booking.getStatus().equals(CANCELLED) || LocalDateTime.now().isAfter(showtime.getStartTime())) {
            log.warn("Cancel denied booking [{}] too late or already cancelled", id);
            throw new IllegalStateException("Booking can not be cancelled");
        }
        booking.setStatus(CANCELLED);
        int updateSeat = showtime.getSeatBooked() - booking.getSeats();
        if (updateSeat < 0) {
            log.warn("Seats cannot be less than 0");
            throw new IllegalArgumentException("Seats cannot be less than 0");
        }
        showtime.setSeatBooked(updateSeat);
        log.info("Booking with id [{}] has been cancelled by [{}]", id, booking.getUser().getId());
        return bookingRepository.save(booking);
    }

    public Page<Map<String,Object>> findAllMoviesRevenue(int page, int size, LocalDateTime startDate, LocalDateTime endDate) {
        Page<Object[]> result = bookingRepository.findAllMoviesRevenue(PageRequest.of(page, size), startDate, endDate);
        List<Map<String,Object>> list = new ArrayList<>();
        for (Object[] obj : result) {
            Map<String,Object> map = new HashMap<>();
            map.put("id",obj[0]);
            map.put("title",obj[1]);
            map.put("total",obj[2]);
            list.add(map);
        }
        return new PageImpl<>(list, PageRequest.of(page, size), result.getTotalElements());
    }

    public Page<Map<String,Object>> findAllCinemasRevenue(int page, int size, LocalDateTime startDate, LocalDateTime endDate) {
        Page<Object[]> result = bookingRepository.findAllCinemasRevenue(PageRequest.of(page, size), startDate, endDate);
        List<Map<String,Object>> list = new ArrayList<>();
        for (Object[] obj : result) {
            Map<String,Object> map = new HashMap<>();
            map.put("id",obj[0]);
            map.put("name",obj[1]);
            map.put("total",obj[2]);
            list.add(map);
        }
        return new PageImpl<>(list, PageRequest.of(page, size), result.getTotalElements());
    }
}

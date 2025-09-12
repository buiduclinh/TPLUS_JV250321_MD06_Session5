package org.example.ra.controller;

import jakarta.validation.Valid;
import org.example.ra.model.Booking;
import org.example.ra.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/booking")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> create(@RequestParam Long userId, @RequestParam Long showtimeId,@Valid @RequestBody Booking booking) {
        return ResponseEntity.ok(bookingService.create(userId, showtimeId, booking));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Booking> cancel(@PathVariable Long id){
        return ResponseEntity.ok(bookingService.cancel(id));
    }

    @GetMapping("/findAllMoviesRevenue")
    public ResponseEntity<Page<Map<String,Object>>> findAllMoviesRevenue(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "5") int size,@RequestParam LocalDateTime startDate,@RequestParam LocalDateTime endDate) {
        return ResponseEntity.ok(bookingService.findAllMoviesRevenue(page, size, startDate, endDate));
    }

    @GetMapping("/findAllCinemasRevenue")
    public ResponseEntity<Page<Map<String,Object>>> findAllCinemasRevenue(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "5") int size,@RequestParam LocalDateTime startDate,@RequestParam LocalDateTime endDate) {
        return ResponseEntity.ok(bookingService.findAllCinemasRevenue(page, size, startDate, endDate));
    }
}

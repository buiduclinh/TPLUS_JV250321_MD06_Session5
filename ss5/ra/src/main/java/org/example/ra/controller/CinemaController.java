package org.example.ra.controller;

import jakarta.validation.Valid;
import org.example.ra.model.Cinema;
import org.example.ra.service.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cinemas")
public class CinemaController {
    @Autowired
    private CinemaService  cinemaService;

    @GetMapping
    public ResponseEntity<Page<Cinema>> findAllCinemas(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(cinemaService.findAllCinemas(page,size,Sort.by("id").ascending()));
    }

    @PostMapping
    public ResponseEntity<Cinema> createCinema(@RequestParam Long movieId,@Valid @RequestBody Cinema cinema) {
        return ResponseEntity.ok(cinemaService.createCinema(movieId,cinema));
    }
}

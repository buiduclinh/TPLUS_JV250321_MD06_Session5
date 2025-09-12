package org.example.ra.controller;

import jakarta.validation.Valid;
import org.example.ra.model.Showtime;
import org.example.ra.service.ShowtimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/showtime")
public class ShowtimeController {
    @Autowired
    private ShowtimeService showtimeService;

    public ResponseEntity<Page<Showtime>> showTimeListByMovie(@RequestParam Long movieId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(showtimeService.getByMovieId(movieId,page,size, Sort.by(Sort.Direction.DESC,"time")));
    }

    public ResponseEntity<Showtime> create(@RequestParam Long movieId, @RequestParam Long cinemaId,@Valid @RequestBody Showtime showtime) {
        return ResponseEntity.ok(showtimeService.create(movieId,cinemaId,showtime));
    }
}

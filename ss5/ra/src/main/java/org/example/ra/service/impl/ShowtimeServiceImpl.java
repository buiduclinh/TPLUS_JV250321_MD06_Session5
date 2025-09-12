package org.example.ra.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.example.ra.model.Movie;
import org.example.ra.model.Showtime;
import org.example.ra.repo.ShowtimeRepository;
import org.example.ra.service.CinemaService;
import org.example.ra.service.MovieService;
import org.example.ra.service.ShowtimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ShowtimeServiceImpl implements ShowtimeService {
    @Autowired
    private ShowtimeRepository showtimeRepository;

    @Autowired
    private MovieService movieService;

    @Autowired
    private CinemaService cinemaService;

    public Page<Showtime> getByMovieId(Long movieId, int page, int size, Sort sort) {
        return showtimeRepository.findShowtimeByMovie_IdOrderByStartTime(movieId, PageRequest.of(page, size, sort));
    }

    public Showtime create(Long movieId, Long cinemaId, Showtime showtime) {
        try {
            showtime.setMovie(movieService.findById(movieId));
            showtime.setCinema(cinemaService.findCinemaById(cinemaId));
            log.info("Created cinema [{}]", cinemaId);
            return showtimeRepository.save(showtime);
        }catch (Exception e) {
            log.error("Failed to create cinema [{}]", cinemaId, e);
            throw e;
        }
    }

    public Showtime getById(Long id) {
        return showtimeRepository.findById(id).orElseThrow(() -> {
            log.error("Failed to find cinema [{}]", id);
            return new EntityNotFoundException("Can't found show time with id:" + id);
        });
    }
}

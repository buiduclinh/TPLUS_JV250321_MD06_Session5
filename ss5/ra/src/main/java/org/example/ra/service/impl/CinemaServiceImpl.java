package org.example.ra.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.example.ra.model.Cinema;
import org.example.ra.model.Movie;
import org.example.ra.repo.CinemaRepository;
import org.example.ra.service.CinemaService;
import org.example.ra.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Slf4j
@Service
public class CinemaServiceImpl implements CinemaService {
    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private MovieService movieService;

    public Page<Cinema> findAllCinemas(int page, int size, Sort sort) {
        return cinemaRepository.findAll(PageRequest.of(page, size, sort));
    }

    public Cinema findCinemaById(Long id) {
        return cinemaRepository.findById(id).orElseThrow(()
                -> {
            log.error("Cinema not found with id [{}]", id);
            return new RuntimeException("Cinema not found with id:" + id);
        });
    }

    public Cinema createCinema(Long movieId, Cinema cinema) {
        Movie movie = movieService.findById(movieId);
        if (cinema.getMovieList() == null) {
            cinema.setMovieList(new ArrayList<>());
        }
        try {
            cinema.getMovieList().add(movie);
            log.info("Creating cinema [{}]", cinema);
            return cinemaRepository.save(cinema);
        }catch (Exception e){
            log.error("Failed to create cinema [{}]", cinema);
            throw e;
        }
    }
}

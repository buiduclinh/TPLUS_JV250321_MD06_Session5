package org.example.ra.service;

import org.example.ra.model.Cinema;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public interface CinemaService {
    Page<Cinema> findAllCinemas(int page, int size, Sort sort);
    Cinema findCinemaById(Long id);
    Cinema createCinema(Long movieId, Cinema cinema);
}

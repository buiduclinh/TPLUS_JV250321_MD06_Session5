package org.example.ra.service;

import org.example.ra.model.Showtime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public interface ShowtimeService {
    Page<Showtime> getByMovieId(Long movieId, int page, int size, Sort sort);
    Showtime create(Long movieId, Long cinemaId,Showtime showtime);
    Showtime getById(Long id);
}

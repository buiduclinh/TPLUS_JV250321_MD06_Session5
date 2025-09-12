package org.example.ra.service;

import org.example.ra.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

public interface MovieService {
    Page<Movie> movieList(int size, int page, Sort sort);
    Page<Movie> search(String title, int size, int page, Sort sort);
    Movie findById(Long id);
    Movie create(MultipartFile avatar, Movie movie);
    Movie update(Movie movie);
    void delete(Long id);
}

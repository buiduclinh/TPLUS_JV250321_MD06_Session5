package org.example.ra.service.impl;

import com.cloudinary.Cloudinary;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.example.ra.model.Movie;
import org.example.ra.repo.MovieRepository;
import org.example.ra.service.CloudinaryService;
import org.example.ra.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@Slf4j
@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private MovieRepository movieRepository;

    public Page<Movie> movieList(int size, int page, Sort sort) {
        return movieRepository.findAll(PageRequest.of(page, size, sort));
    }

    public Page<Movie> search(String title, int size, int page, Sort sort) {
        return movieRepository.findMovieByTitleContainingIgnoreCase(title,(PageRequest.of(page, size, sort)));
    }

    public Movie findById(Long id) {
        return movieRepository.findById(id).orElseThrow(() ->{
            log.error("Movie with id [{}] not found", id);
               return new RuntimeException("Can't find movie by id>" + id);});
    }

    public Movie create(MultipartFile avatar, Movie movie) {
        try {
            movie.setPoster(cloudinaryService.avatar(avatar));
            log.info("Creating movie with id [{}]", movie.getId());
            return movieRepository.save(movie);
        } catch (IOException e) {
            log.error("Failed to upload avatar [{}]", movie.getId(), e);
            throw new RuntimeException(e);
        } catch (Exception e) {
            log.error("Failed to create movie with id [{}]", movie.getId(), e);
            throw e;
        }
    }

    public Movie update(Movie movie) {
        Movie oldMovie = findById(movie.getId());
        if(movie.getPoster() != null && !movie.getPoster().isEmpty()){
            oldMovie.setPoster(movie.getPoster());
        }
        log.info("Updating movie with id [{}]", movie.getId());
        return movieRepository.save(oldMovie);
    }

    public void delete(Long id) {
        if (movieRepository.existsById(id)) {
            movieRepository.deleteById(id);
            log.info("Deleting movie with id [{}]", id);
        }
    }
}

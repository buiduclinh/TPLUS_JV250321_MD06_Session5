package org.example.ra.controller;

import jakarta.validation.Valid;
import org.example.ra.model.Movie;
import org.example.ra.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/movies")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @GetMapping
    public ResponseEntity<Page<Movie>> findAll(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(movieService.movieList(page,size, Sort.by("id").ascending()));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Movie>> search(@RequestParam String title,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(movieService.search(title,page,size, Sort.by("title").descending()));
    }

    @PostMapping
    public ResponseEntity<Movie> create(@RequestParam MultipartFile avatar, @Valid @RequestBody Movie movie) {
        return ResponseEntity.ok(movieService.create(avatar,movie));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> update(@Valid @RequestBody Movie movie) {
        return ResponseEntity.ok(movieService.update(movie));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        movieService.delete(id);
        return ResponseEntity.ok().build();
    }
}

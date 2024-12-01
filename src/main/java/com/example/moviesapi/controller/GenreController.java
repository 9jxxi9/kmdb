package com.example.moviesapi.controller;

import com.example.moviesapi.dto.GenreRequest;
import com.example.moviesapi.exceptions.InvalidData;
import com.example.moviesapi.exceptions.NotFound;
import com.example.moviesapi.model.Genre;
import com.example.moviesapi.model.Movie;
import com.example.moviesapi.service.GenreService;
import com.example.moviesapi.service.MovieService;
import com.example.moviesapi.view.Views;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@Validated
@RequestMapping("/api/genres")
public class GenreController {
    @Autowired
    private GenreService genreService;
    @Autowired
    private MovieService movieService;

    @PostMapping
    @JsonView(Views.BasicGenre.class)
    public ResponseEntity<Genre> createGenre(@Valid @RequestBody GenreRequest genreRequest) {
        Genre genre = null;
        try {
            genre = new Genre(genreRequest.getName(), genreRequest.processMovies(movieService));
        } catch (Exception e) {
            throw new InvalidData("Invalid data to create genre");
        }
        return ResponseEntity.created(null).body(genreService.createGenre(genre));
    }


    @GetMapping
    @JsonView(Views.BasicGenre.class)
    public ResponseEntity<Page<Genre>> getGenres(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int limit) {
        Pageable pageable = Pageable.ofSize(limit).withPage(page);
        Page<Genre> genres = genreService.getGenres(pageable);
        return ResponseEntity.ok(genres);
    }

    @GetMapping("/{id}")
    @JsonView(Views.BasicGenre.class)
    public ResponseEntity<Genre> getGenre(@PathVariable Long id) {
        return ResponseEntity.ok(genreService.findById(id).orElseThrow(() -> new NotFound("Genre not found")));
    }

    @PatchMapping("/{id}")
    @JsonView(Views.BasicGenre.class)
    public ResponseEntity<Genre> modifyGenre(@PathVariable Long id, @RequestBody Genre genre) {
        try {
            genreService.findById(id).orElseThrow(() -> new NotFound("Genre not found"));
            genreService.modifyGenre(id, genre);
        } catch (Exception e) {
            throw new InvalidData("Invalid data to modify genre");
        }
        return ResponseEntity.ok(genreService.findById(id).get());
    }

    @GetMapping("/{id}/movies")
    @JsonView(Views.WithMoviesGenre.class)
    public ResponseEntity<Collection<Movie>> getMoviesByGenre(@PathVariable Long id) {
        genreService.findById(id).orElseThrow(() -> new NotFound("Genre not found"));
        return ResponseEntity.ok(genreService.getMovies(id).orElseThrow(() -> new NotFound("No movies found for genre")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenre(@PathVariable Long id, @RequestParam(required = false) Boolean force) {
        Genre genre = genreService.findById(id).orElseThrow(() -> new NotFound("Genre not found"));
        if (force == null) {
            force = false;
        }
        if (!genre.getMovies().isEmpty() && !force) {
            throw new InvalidData("Genre " + genre.name + " has movies associated");
        } else if (force) {
            genre.getMovies().forEach(movie -> movie.getGenres().remove(genre));
            genreService.modifyGenre(id, genre);
        }
        genreService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

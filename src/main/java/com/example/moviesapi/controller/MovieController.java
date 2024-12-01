package com.example.moviesapi.controller;

import com.example.moviesapi.dto.MovieRequest;
import com.example.moviesapi.exceptions.InvalidData;
import com.example.moviesapi.exceptions.NotFound;
import com.example.moviesapi.model.Actor;
import com.example.moviesapi.model.Genre;
import com.example.moviesapi.model.Movie;
import com.example.moviesapi.service.ActorService;
import com.example.moviesapi.service.GenreService;
import com.example.moviesapi.service.MovieService;
import com.example.moviesapi.view.Views;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Year;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@Validated
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;
    @Autowired
    private GenreService genreService;
    @Autowired
    private ActorService actorService;
    @Autowired
    private PagedResourcesAssembler<Movie> pagedResourcesAssembler;

    @PostMapping
    public ResponseEntity<Movie> createMovie(@Valid @RequestBody MovieRequest movieRequest) {
        Movie movie = null;
        Set<Actor> actors = null;
        Set<Genre> genres = null;
        try {
            actors = movieRequest.processActors(actorService);
            genres = movieRequest.processGenre(genreService);
        } catch (Exception e) {
            throw new InvalidData("Invalid actor or genre ids");
        }
        try {
            movie = new Movie(movieRequest.getTitle(), movieRequest.getReleaseYear(), movieRequest.getDuration(), genres, actors);
        } catch (Exception e) {
            throw new InvalidData("Invalid data to create movie");
        }
        return ResponseEntity.created(null).body(movieService.create(movie));
    }

    @GetMapping
    public ResponseEntity<PagedModel<Movie>> getMovies(
            @RequestParam(required = false) Year year,
            @RequestParam(required = false) Long genre,
            @RequestParam(required = false) Long actor,
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Movie> movies = movieService.getMoviesByParams(year, genre, actor, title, pageable).orElseThrow(() -> new NotFound("Movies not found"));
        PagedModel<EntityModel<Movie>> pagedModelEntity = pagedResourcesAssembler.toModel(movies);
        PagedModel<Movie> pagedModel = PagedModel.of(
                pagedModelEntity.getContent().stream().map(EntityModel::getContent).collect(Collectors.toList()),
                pagedModelEntity.getMetadata(),
                pagedModelEntity.getLinks()
        );
        return ResponseEntity.ok(pagedModel);
    }

    @PatchMapping("/{id}")
    @JsonView(Views.WithActorsMovie.class)
    public ResponseEntity<Movie> modifyMovie(@PathVariable Long id, @RequestBody MovieRequest movieRequest) {
        Movie movie = null;
        try {
            movieService.findById(id).orElseThrow(() -> new NotFound("Movie not found"));
            movie = new Movie(movieRequest.getTitle(), movieRequest.getReleaseYear(), movieRequest.getDuration(), movieRequest.processGenre(genreService), movieRequest.processActors(actorService));

        } catch (Exception e) {
            throw new InvalidData("Invalid data to modify movie");
        }
        return ResponseEntity.ok(movieService.modifyMovie(id, movie).get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieService.findById(id).orElseThrow(() -> new NotFound("Movie not found"));

        movieService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @JsonView(Views.BasicMovie.class)
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.findById(id).orElseThrow(() -> new NotFound("Movie not found")));
    }

    @GetMapping("/{id}/actors")
    @JsonView(Views.WithActorsMovie.class)
    public ResponseEntity<Movie> getMovieWithActors(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.findById(id).orElseThrow(() -> new NotFound("Movie not found")));
    }

}
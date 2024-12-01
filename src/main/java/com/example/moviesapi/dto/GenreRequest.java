package com.example.moviesapi.dto;

import com.example.moviesapi.model.Movie;
import com.example.moviesapi.service.MovieService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GenreRequest {
    public List<Long> movies;
    @NotBlank
    @NotNull
    private String name;

    public GenreRequest() {
    }

    public GenreRequest(String name, List<Long> movies) {
        this.name = name;
    }

    public GenreRequest(String action) {
        this.name = action;
    }

    public Set<Movie> processMovies(MovieService movieService) {
        return (this.movies == null) ? null : this.movies.stream()
                .map(movieService::createFromId)
                .collect(Collectors.toSet());
    }

    public String getName() {
        return name;
    }

    public void setName(Object o) {
        this.name = (String) o;
    }
}

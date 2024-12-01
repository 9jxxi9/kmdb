package com.example.moviesapi.dto;

import com.example.moviesapi.model.Movie;
import com.example.moviesapi.service.MovieService;
import com.example.moviesapi.validator.ValidDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ActorRequest {
    public List<Long> movies;
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    @ValidDate
    private Date birthDate;

    public ActorRequest() {
    }

    public ActorRequest(String name, Date birthDate, List<Long> movies) {
        this.name = name;
        this.birthDate = birthDate;
        this.movies = movies;
    }


    public Set<Movie> processMovies(MovieService movieService) {
        return (this.movies == null) ? null : this.movies.stream()
                .map(movieService::createFromId)
                .collect(Collectors.toSet());
    }

    public String getName() {
        return name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setName(Object o) {
        this.name = (String) o;
    }
}

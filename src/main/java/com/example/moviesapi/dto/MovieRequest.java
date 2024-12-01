package com.example.moviesapi.dto;

import com.example.moviesapi.model.Actor;
import com.example.moviesapi.model.Genre;
import com.example.moviesapi.service.ActorService;
import com.example.moviesapi.service.GenreService;
import com.example.moviesapi.validator.ValidDuration;
import com.example.moviesapi.validator.ValidReleaseYear;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.sql.Time;
import java.time.Year;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MovieRequest {
    @NotNull
    @ValidDuration
    public Time duration;

    public List<Long> genres;
    public List<Long> actors;
    @NotBlank
    @NotNull
    private String title;
    @ValidReleaseYear
    private Year releaseYear;

    public MovieRequest() {
    }

    public MovieRequest(String title, Year releaseYear, Time duration, List<Long> genreIds, List<Long> actorIds) {
        this.title = title;
        this.releaseYear = releaseYear;
        this.duration = duration;
        this.genres = genreIds;
        this.actors = actorIds;

    }

    public Set<Actor> processActors(ActorService actorService) {
        return (this.actors == null) ? null : this.actors.stream()
                .map(actorService::createFromId)
                .collect(Collectors.toSet());
    }

    public Set<Genre> processGenre(GenreService genreService) {
        return (this.genres == null) ? null : this.genres.stream()
                .map(genreService::createFromId)
                .collect(Collectors.toSet());
    }

    public String getTitle() {
        return title;
    }

    public Year getReleaseYear() {
        return releaseYear;
    }


    public Time getDuration() {
        return duration;
    }


    public void setTitle(Object o) {
        this.title = (String) o;
    }
}
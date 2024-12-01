package com.example.moviesapi.model;

import com.example.moviesapi.view.Views;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;

import java.sql.Time;
import java.time.Year;
import java.util.Set;

@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ_MOVIE", allocationSize = 1)
    @JsonView({Views.BasicMovie.class, Views.WithMoviesGenre.class, Views.WithMoviesActor.class})
    public long id;
    @JsonView({Views.BasicMovie.class, Views.WithMoviesGenre.class, Views.WithMoviesActor.class})
    public String title;
    @JsonView({Views.BasicMovie.class, Views.WithMoviesGenre.class, Views.WithMoviesActor.class})
    @Temporal(TemporalType.DATE)
    public Year releaseYear;

    @JsonView({Views.BasicMovie.class, Views.WithMoviesGenre.class, Views.WithMoviesActor.class})
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    public Time duration;

    @ManyToMany
    @JoinTable(
            name = "movie_actor",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id"))
    @JsonIgnoreProperties("movies")
    @JsonView({Views.WithActorsMovie.class, Views.WithMoviesGenre.class})
    Set<Actor> actors;

    @ManyToMany
    @JoinTable(
            name = "movie_genre",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    @JsonIgnoreProperties("movies")
    @JsonView({Views.BasicMovie.class})
    Set<Genre> genres;

    public Movie() {
    }

    public Movie(String title, Year releaseYear, Time duration) {
        this.title = title;
        this.releaseYear = releaseYear;
        this.duration = duration;
    }

    public Movie(String title, Year releaseYear, Time duration, Set<Genre> genres, Set<Actor> actors) {
        this.title = title;
        this.releaseYear = releaseYear;
        this.duration = duration;
        this.genres = genres;
        this.actors = actors;
    }


    public Set<Genre> getGenres() {
        return this.genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public Set<Actor> getActors() {
        return actors;
    }

    public void setActors(Set<Actor> actors) {
        this.actors = actors;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Year getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Year releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Time getDuration() {
        return duration;
    }

    public void setDuration(Time duration) {
        this.duration = duration;
    }
}

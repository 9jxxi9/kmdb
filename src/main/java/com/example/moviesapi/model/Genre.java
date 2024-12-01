package com.example.moviesapi.model;

import com.example.moviesapi.view.Views;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ_GENRE", allocationSize = 1)
    @JsonView({Views.BasicGenre.class, Views.BasicMovie.class})
    public long id;
    @JsonView({Views.BasicGenre.class, Views.BasicMovie.class})
    @NotBlank(message = "Name is mandatory")
    public String name;
    @ManyToMany(mappedBy = "genres")
    @JsonIgnoreProperties("genres")
    @JsonView(Views.WithMoviesGenre.class)
    Set<Movie> movies = new HashSet<>();

    public Genre() {
    }

    public Genre(String name) {
        this.name = name;
    }

    public Genre(String name, Set<Movie> movies) {
        this.name = name;
        this.movies = movies;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
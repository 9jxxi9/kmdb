package com.example.moviesapi.model;

import com.example.moviesapi.view.Views;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;

@Entity
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ_ACTOR", allocationSize = 1)
    @JsonView({Views.WithActorsMovie.class, Views.BasicActor.class, Views.WithMoviesGenre.class})
    private long id;

    @JsonView({Views.WithActorsMovie.class, Views.BasicActor.class, Views.WithMoviesGenre.class})
    private String name;

    @JsonView({Views.WithActorsMovie.class, Views.BasicActor.class, Views.WithMoviesGenre.class})
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @ManyToMany(mappedBy = "actors")
    @JsonIgnoreProperties("actors")
    @JsonView({Views.WithMoviesActor.class, Views.WithActorsMovie.class})
    private Set<Movie> movies;

    public Actor() {
    }

    public Actor(String name, Date birthDate, Set<Movie> movies) {
        this.name = name;
        this.birthDate = birthDate;
        this.movies = movies;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

}
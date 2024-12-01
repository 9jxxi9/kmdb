package com.example.moviesapi.service;

import com.example.moviesapi.model.Genre;
import com.example.moviesapi.model.Movie;
import com.example.moviesapi.repository.GenreRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Validated
@Service
public class GenreService extends EntityService<Genre, Long> {

    @Autowired
    private GenreRepository GenreRepos;

    @Autowired
    public GenreService(GenreRepository repository) {
        super(repository);
    }

    public Optional<Genre> modifyGenre(Long id, Genre genre) {
        Optional<Genre> genreOptional = findById(id);
        if (genreOptional.isPresent()) {
            Genre genreToModify = genreOptional.get();
            if (genre.getName() != null) {
                genreToModify.setName(genre.getName());

            }
            GenreRepos.save(genreToModify);
        }
        return genreOptional;
    }
    public Page<Genre> getGenres(Pageable pageable) {
        return GenreRepos.findAll(pageable);
    }


    public Optional<Collection<Movie>> getMovies(Long id) {
        return findById(id).map(Genre::getMovies);
    }


    public Genre createGenre(@Valid Genre genre) {
        Set<Movie> movies = genre.getMovies();
        if (movies != null) {
            for (Movie movie : movies) {
                movie.getGenres().add(genre);
            }
        }
        return GenreRepos.save(genre);
    }

}
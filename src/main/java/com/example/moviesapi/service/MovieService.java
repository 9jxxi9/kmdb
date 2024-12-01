package com.example.moviesapi.service;

import com.example.moviesapi.model.Movie;
import com.example.moviesapi.model.MovieSpecification;
import com.example.moviesapi.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.Year;
import java.util.Optional;

@Service
@Validated
public class MovieService extends EntityService<Movie, Long> {

    @Autowired
    private MovieRepository MovieRepo;

    @Autowired
    public MovieService(MovieRepository repository) {
        super(repository);
    }

    public Optional<Page<Movie>> getMoviesByParams(Year releaseYear, Long genreId, Long actorId, String title, Pageable pageable) {
        Specification<Movie> spec = Specification.where(MovieSpecification.hasReleaseYear(releaseYear))
                .and(MovieSpecification.hasGenreId(genreId))
                .and(MovieSpecification.hasActorId(actorId))
                .and(MovieSpecification.hasTitleContaining(title));
        Page<Movie> movies = MovieRepo.findAll(spec, pageable);
        return Optional.of(MovieRepo.findAll(spec, pageable));
    }

    public Optional<Movie> modifyMovie(Long id, Movie movie) {
        Optional<Movie> movieOptional = MovieRepo.findById(id);
        if (movieOptional.isPresent()) {
            Movie movieToModify = movieOptional.get();
            if (movie.getTitle() != null) {
                movieToModify.setTitle(movie.getTitle());
            }
            if (movie.getReleaseYear() != null) {
                movieToModify.setReleaseYear(movie.getReleaseYear());
            }
            if (movie.getDuration() != null) {
                movieToModify.setDuration(movie.getDuration());
            }
            if (movie.getGenres() != null) {
                movieToModify.setGenres(movie.getGenres());
            }
            if (movie.getActors() != null) {
                movieToModify.setActors(movie.getActors());
            }
            return Optional.of(MovieRepo.save(movieToModify));
        }
        return Optional.empty();
    }

}
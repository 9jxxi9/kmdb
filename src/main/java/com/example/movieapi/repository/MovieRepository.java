package main.java.com.example.movieapi.repository;

import com.example.movieapi.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    // Search for movies by genre
    List<Movie> findByGenres_Id(Long genreId);

   // Search for movies by title (case sensitive)
    List<Movie> findByTitleContainingIgnoreCase(String title);
}
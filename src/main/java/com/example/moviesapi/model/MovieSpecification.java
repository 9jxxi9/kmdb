package com.example.moviesapi.model;

import org.springframework.data.jpa.domain.Specification;

import java.time.Year;

public class MovieSpecification {

    public static Specification<Movie> hasReleaseYear(Year releaseYear) {
        return (root, query, criteriaBuilder) ->
                releaseYear == null ? null : criteriaBuilder.equal(root.get("releaseYear"), releaseYear);
    }

    public static Specification<Movie> hasGenreId(Long genreId) {
        return (root, query, criteriaBuilder) ->
                genreId == null ? null : criteriaBuilder.equal(root.join("genres").get("id"), genreId);
    }

    public static Specification<Movie> hasActorId(Long actorId) {
        return (root, query, criteriaBuilder) ->
                actorId == null ? null : criteriaBuilder.equal(root.join("actors").get("id"), actorId);
    }

    public static Specification<Movie> hasTitleContaining(String title) {
        return (root, query, criteriaBuilder) ->
                title == null ? null : criteriaBuilder.like(root.get("title"), "%" + title + "%");
    }
}

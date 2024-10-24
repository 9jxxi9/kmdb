package main.java.com.example.movieapi.repository;

import com.example.movieapi.model.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActorRepository extends JpaRepository<Actor, Long> {

    // Search for actors by name (case sensitive)
    List<Actor> findByNameContainingIgnoreCase(String name);
}
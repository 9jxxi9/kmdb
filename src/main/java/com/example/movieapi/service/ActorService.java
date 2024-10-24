package main.java.com.example.movieapi.service;

import com.example.movieapi.model.Actor;
import com.example.movieapi.repository.ActorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActorService {
    private final ActorRepository actorRepository;

    public ActorService(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    public List<Actor> findAll() {
        return actorRepository.findAll();
    }

    public Optional<Actor> findById(Long id) {
        return actorRepository.findById(id);
    }

    public Actor save(Actor actor) {
        return actorRepository.save(actor);
    }

    public void deleteById(Long id) {
        actorRepository.deleteById(id);
    }

    public List<Actor> findByName(String name) {
        return actorRepository.findByNameContainingIgnoreCase(name);
    }
}
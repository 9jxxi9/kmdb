package com.example.moviesapi.service;

import com.example.moviesapi.exceptions.NotFound;
import com.example.moviesapi.model.Actor;
import com.example.moviesapi.model.Movie;
import com.example.moviesapi.repository.ActorRepository;
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
public class ActorService extends EntityService<Actor, Long> {

    @Autowired
    private ActorRepository ActorRepo;

    @Autowired
    public ActorService(ActorRepository repository) {
        super(repository);
    }

    public Actor createActor(@Valid Actor actor) {
        Set<Movie> movies = actor.getMovies();
        if (movies != null) {
            for (Movie movie : movies) {
                movie.getActors().add(actor);
            }
        }
        return ActorRepo.save(actor);
    }
    public Page<Actor> getActors(Pageable pageable, String name) {
        Page<Actor> actors;
        if (name != null && !name.isEmpty()) {
            actors = ActorRepo.findByNameContaining(name, pageable);
        } else {
            actors = ActorRepo.findAll(pageable);
        }
        if (actors.isEmpty()) {
            throw new NotFound("Actors not found");
        }
        return actors;
    }

    public Collection<Movie> getMovies(Long id) {
        return findById(id).map(Actor::getMovies).orElse(null);
    }

    public Optional<Actor> modifyActor(Long id, Actor actor) {
        Optional<Actor> actorOptional = findById(id);
        if (actorOptional.isPresent()) {
            Actor actorToModify = actorOptional.get();
            if (actor.getName() != null) {
                actorToModify.setName(actor.getName());

            }
            if (actor.getBirthDate() != null) {
                actorToModify.setBirthDate(actor.getBirthDate());

            }

            ActorRepo.save(actorToModify);
        }
        return actorOptional;
    }


}
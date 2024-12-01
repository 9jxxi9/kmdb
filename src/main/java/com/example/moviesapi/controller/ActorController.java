package com.example.moviesapi.controller;


import com.example.moviesapi.dto.ActorRequest;
import com.example.moviesapi.exceptions.InvalidData;
import com.example.moviesapi.exceptions.NotFound;
import com.example.moviesapi.model.Actor;
import com.example.moviesapi.service.ActorService;
import com.example.moviesapi.service.MovieService;
import com.example.moviesapi.view.Views;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/actors")
@Validated
public class ActorController {
    @Autowired
    private ActorService actorService;
    @Autowired
    private MovieService Mov;


    @PostMapping
    @JsonView(Views.WithMoviesActor.class)
    public ResponseEntity<Actor> createActor(@Valid @RequestBody ActorRequest actorRequest) {
        Actor actor = null;
        try {
            actor = new Actor(actorRequest.getName(), actorRequest.getBirthDate(), actorRequest.processMovies(Mov));
        } catch (Exception e) {
            throw new InvalidData("Invalid data to create actor");
        }
        return ResponseEntity.created(null).body(actorService.createActor(actor));
    }

    @GetMapping
    public ResponseEntity<Page<Actor>> getActors(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int limit, @RequestParam(required = false) String name) {
        Pageable pageable = Pageable.ofSize(limit).withPage(page);
        Page<Actor> actors = actorService.getActors(pageable, name);
        return ResponseEntity.ok(actors);
    }

    @GetMapping("/{id}")
    @JsonView(Views.BasicActor.class)
    public ResponseEntity<Actor> getActor(@PathVariable Long id) {
        return ResponseEntity.ok(actorService.findById(id).orElseThrow(() -> new NotFound("Actor not found")));
    }

    @PatchMapping("/{id}")
    @JsonView(Views.BasicActor.class)
    public ResponseEntity<Actor> modifyActor(@PathVariable Long id, @RequestBody ActorRequest actorRequest) {
        actorService.findById(id).orElseThrow(() -> new NotFound("Actor not found"));
        Actor actor = new Actor(actorRequest.getName(), actorRequest.getBirthDate(), actorRequest.processMovies(Mov));
        try {
            actorService.modifyActor(id, actor);
        } catch (Exception e) {
            throw new InvalidData("Invalid data to modify actor");
        }
        return ResponseEntity.ok(actorService.modifyActor(id, actor).get());
    }

    @GetMapping("/{id}/movies")
    @JsonView(Views.WithMoviesActor.class)
    public ResponseEntity<Actor> getMoviesByActor(@PathVariable Long id) {
        return ResponseEntity.ok(actorService.findById(id).orElseThrow(() -> new NotFound("Actor not found")));
    }

    @DeleteMapping("/{id}")
    @JsonView(Views.BasicActor.class)
    public ResponseEntity<Void> deletActor(@PathVariable Long id, @RequestParam(required = false) Boolean force) {
        Actor actor = actorService.findById(id).orElseThrow(() -> new NotFound("Actor not found"));
        if (force == null) {
            force = false;
        }
        if (!actor.getMovies().isEmpty() && !force) {
            throw new InvalidData("Actor " + actor.getName() + " has movies associated");
        } else if (force) {
            actor.getMovies().forEach(movie -> movie.getActors().remove(actor));
            actorService.modifyActor(id, actor);
        }
        actorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

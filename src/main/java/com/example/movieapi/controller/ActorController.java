package main.java.com.example.movieapi.controller;

import com.example.movieapi.model.Actor;
import com.example.movieapi.service.ActorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/actors")
public class ActorController {
    private final ActorService actorService;

    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping
    public List<Actor> getAllActors() {
        return actorService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Actor> getActorById(@PathVariable Long id) {
        return actorService.findById(id)
                .map(actor -> ResponseEntity.ok(actor))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public List<Actor> searchActors(@RequestParam String name) {
        return actorService.findByName(name);
    }

    @PostMapping
    public ResponseEntity<Actor> createActor(@Valid @RequestBody Actor actor) {
        Actor createdActor = actorService.save(actor);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdActor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActor(@PathVariable Long id) {
        actorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
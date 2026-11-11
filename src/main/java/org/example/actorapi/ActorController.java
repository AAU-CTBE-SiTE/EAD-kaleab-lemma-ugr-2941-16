package org.example.actorapi;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/actors")
@AllArgsConstructor
public class ActorController {
    private final ActorRepository repository;

    // GET all actors: find all from the repository
    @GetMapping
    public List<Actor> getAllActors() {
        return  repository.findAll();
    }

    // GET one actor by ID: search actor by ID
    @GetMapping("/{id}")
    public ResponseEntity<Actor> getActorById(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST: add an actor
    @PostMapping
    public ResponseEntity<Actor> addActor(@RequestBody Actor actor) {
        Actor saved = repository.save(actor);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // PUT: replace the whole actor
    @PutMapping("/{id}")
    public ResponseEntity<Actor> updateActor(@PathVariable Long id, @RequestBody Actor newData) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setFirstName(newData.getFirstName());
                    existing.setLastName(newData.getLastName());
                    return ResponseEntity.ok(repository.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // PATCH: change only the fields that were sent
    @PatchMapping("/{id}")
    public ResponseEntity<Actor> patchActor(@PathVariable Long id, @RequestBody Actor partial) {
        return repository.findById(id)
                .map(existing -> {
                    if (partial.getFirstName() != null) {
                        existing.setFirstName(partial.getFirstName());
                    }
                    if (partial.getLastName() != null) {
                        existing.setLastName(partial.getLastName());
                    }
                    return ResponseEntity.ok(repository.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE an actor
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActor(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
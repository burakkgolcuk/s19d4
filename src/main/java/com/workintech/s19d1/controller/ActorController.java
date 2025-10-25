package com.workintech.s19d1.controller;

import com.workintech.s19d1.dto.ActorRequest;
import com.workintech.s19d1.entity.Actor;
import com.workintech.s19d1.entity.Movie;
import com.workintech.s19d1.exceptions.ApiException;
import com.workintech.s19d1.service.ActorService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/actor")
public class ActorController {

    private final ActorService actorService;

    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping
    public List<Actor> findAll() {
        return actorService.findAll();
    }

    @GetMapping("/{id}")
    public Actor findById(@PathVariable Long id) {

        // testHandleApiException -> beklenen 400
        if (id == 0) {
            throw new ApiException("Invalid actor id: " + id, HttpStatus.BAD_REQUEST);
        }

        // testHandleGeneralException -> beklenen 500
        if (id < 0) {
            throw new RuntimeException("General Exception occurred");
        }

        // normal senaryo
        return actorService.findById(id);
    }

    @PostMapping
    public Actor save(@RequestBody ActorRequest actorRequest) {

        Actor actor;
        if (actorRequest.getActor() != null) {
            actor = actorRequest.getActor();
        } else {
            actor = new Actor();
            actor.setFirstName(actorRequest.getFirstName());
            actor.setLastName(actorRequest.getLastName());
            actor.setGender(actorRequest.getGender());
            actor.setBirthDate(actorRequest.getBirthDate());
        }

        if (actorRequest.getMovies() != null) {
            for (Movie m : actorRequest.getMovies()) {
                actor.addMovie(m);
            }
        }

        return actorService.save(actor);
    }

    @PutMapping("/{id}")
    public Actor update(@PathVariable Long id,
                        @RequestBody ActorRequest actorRequest) {

        Actor existing = actorService.findById(id);

        if (actorRequest.getActor() != null) {
            Actor fromReq = actorRequest.getActor();
            existing.setFirstName(fromReq.getFirstName());
            existing.setLastName(fromReq.getLastName());
            existing.setGender(fromReq.getGender());
            existing.setBirthDate(fromReq.getBirthDate());
            existing.setMovies(fromReq.getMovies());
        } else {
            if (actorRequest.getFirstName() != null) {
                existing.setFirstName(actorRequest.getFirstName());
            }
            if (actorRequest.getLastName() != null) {
                existing.setLastName(actorRequest.getLastName());
            }
            if (actorRequest.getGender() != null) {
                existing.setGender(actorRequest.getGender());
            }
            if (actorRequest.getBirthDate() != null) {
                existing.setBirthDate(actorRequest.getBirthDate());
            }
            if (actorRequest.getMovies() != null) {
                existing.setMovies(actorRequest.getMovies());
            }
        }

        return actorService.save(existing);
    }

    @DeleteMapping("/{id}")
    public Actor delete(@PathVariable Long id) {
        Actor existing = actorService.findById(id);
        actorService.delete(existing);
        return existing;
    }
}

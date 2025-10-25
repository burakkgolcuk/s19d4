package com.workintech.s19d1.controller;

import com.workintech.s19d1.entity.Actor;
import com.workintech.s19d1.entity.Movie;
import com.workintech.s19d1.service.ActorService;
import com.workintech.s19d1.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workintech/movies")
public class MovieController {

    private final MovieService movieService;
    private final ActorService actorService;

    public MovieController(MovieService movieService, ActorService actorService) {
        this.movieService = movieService;
        this.actorService = actorService;
    }

    @GetMapping
    public List<Movie> findAll() {
        return movieService.findAll();
    }

    @GetMapping("/{id}")
    public Movie findById(@PathVariable Long id) {
        return movieService.findById(id);
    }

    // Movie + Actor birlikte kaydetme
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Movie save(@RequestBody Movie movie,
                      @RequestBody(required = false) Actor actor) {
        if (actor != null) {
            // ilişki kur
            movie.addActor(actor);
            actorService.save(actor);
        }
        return movieService.save(movie);
    }

    @PutMapping("/{id}")
    public Movie update(@PathVariable Long id,
                        @RequestBody Movie newMovie) {

        Movie existing = movieService.findById(id);
        existing.setName(newMovie.getName());
        existing.setDirectorName(newMovie.getDirectorName());
        existing.setRating(newMovie.getRating());
        existing.setReleaseDate(newMovie.getReleaseDate());
        // actors ilişkisini güncellemek istersen burada handle edersin
        return movieService.save(existing);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        Movie existing = movieService.findById(id);
        movieService.delete(existing);
    }
}

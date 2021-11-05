package br.com.webcrawler.imdb.controller;

import br.com.webcrawler.imdb.model.Movie;
import br.com.webcrawler.imdb.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Movie> create(@Valid @RequestBody Movie movie) {
        Movie movieSaved = movieService.save(movie);
        return ResponseEntity.ok(movieSaved);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Movie>> readBottom10() {
        List<Movie> movies = movieService.findTop10ByOrderByRate();
        Collections.sort(movies);
        return ResponseEntity.ok(movies);
    }

    @GetMapping(value = "/{movieId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Movie> readById(@PathVariable Integer movieId) {
        return ResponseEntity.ok(movieService.readById(movieId));
    }

    @PutMapping(value = "/{movieId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Movie> update(@PathVariable Integer movieId, @Valid @RequestBody Movie movie) {
        if (!movieService.existsById(movieId)) {
            return ResponseEntity.notFound().build();
        }

        movie.setId(movieId);
        Movie movieSaved = movieService.save(movie);
        return ResponseEntity.ok(movieSaved);
    }

    @DeleteMapping(value = "/{movieId}")
    public ResponseEntity<Void> delete(@PathVariable Integer movieId) {
        if (!movieService.existsById(movieId)) {
            return ResponseEntity.notFound().build();
        }

        movieService.deleteById(movieId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {
        movieService.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
package br.com.webcrawler.imdb.controller;

import br.com.webcrawler.imdb.dto.CommentDto;
import br.com.webcrawler.imdb.dto.MovieDto;
import br.com.webcrawler.imdb.mapper.CommentMapper;
import br.com.webcrawler.imdb.mapper.MovieMapper;
import br.com.webcrawler.imdb.model.Comment;
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

    @Autowired
    private MovieMapper movieMapper;

    @Autowired
    private CommentMapper commentMapper;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MovieDto> create(@Valid @RequestBody MovieDto movieDto) {
        Movie movie = movieMapper.toEntity(movieDto);
        Movie movieSaved = movieService.save(movie);
        return ResponseEntity.ok(movieMapper.toDto(movieSaved));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MovieDto>> readBottom10() {
        List<Movie> movies = movieService.findTop10ByOrderByRate();
        Collections.sort(movies);
        return ResponseEntity.ok(movieMapper.toCollectionDto(movies));
    }

    @GetMapping(value = "/{movieId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MovieDto> readById(@PathVariable Integer movieId) {
        Movie movie = movieService.readById(movieId);
        return ResponseEntity.ok(movieMapper.toDto(movie));
    }

    @PutMapping(value = "/{movieId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MovieDto> update(@PathVariable Integer movieId, @Valid @RequestBody MovieDto movieDto) {
        if (!movieService.existsById(movieId)) {
            return ResponseEntity.notFound().build();
        }

        Movie movie = movieMapper.toEntity(movieDto);
        movie.setId(movieId);
        Movie movieSaved = movieService.save(movie);
        return ResponseEntity.ok(movieMapper.toDto(movieSaved));
    }

    @PostMapping(value = "/{idImdb}/comments", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MovieDto> createComments(@PathVariable String idImdb,  @Valid @RequestBody List<CommentDto> commentsDto) {
        if (!movieService.existsByIdImdb(idImdb)) {
            return ResponseEntity.notFound().build();
        }

        Movie movie = movieService.readByIdImdb(idImdb);
        List<Comment> comments = commentMapper.toCollectionEntity(commentsDto);
        comments.forEach(comment -> {
            comment.setMovie(movie);
        });

        movie.setComments(comments);
        Movie movieSaved = movieService.save(movie);
        return ResponseEntity.ok(movieMapper.toDto(movieSaved));
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
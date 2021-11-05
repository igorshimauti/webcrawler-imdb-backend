package br.com.webcrawler.imdb.controller;

import br.com.webcrawler.imdb.model.Director;
import br.com.webcrawler.imdb.service.DirectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "director")
public class DirectorController {

    @Autowired
    private DirectorService directorService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Director> save(@Valid @RequestBody Director director) {
        Director directorSaved = directorService.save(director);
        return ResponseEntity.ok(directorSaved);
    }
}
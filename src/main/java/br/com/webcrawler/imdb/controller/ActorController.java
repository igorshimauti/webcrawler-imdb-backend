package br.com.webcrawler.imdb.controller;

import br.com.webcrawler.imdb.model.Actor;
import br.com.webcrawler.imdb.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "actor")
public class ActorController {

    @Autowired
    private ActorService actorService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Actor> create(@Valid @RequestBody Actor actor) {
        Actor actorSaved = actorService.save(actor);
        return ResponseEntity.ok(actorSaved);
    }
}
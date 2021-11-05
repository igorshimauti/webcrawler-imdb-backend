package br.com.webcrawler.imdb.service;

import br.com.webcrawler.imdb.exception.BusinessRulesException;
import br.com.webcrawler.imdb.model.Actor;
import br.com.webcrawler.imdb.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ActorService {

    @Autowired
    private ActorRepository actorRepository;

    @Transactional
    public Actor save(Actor actor) {
        if (actorRepository.existsByName(actor.getName())) {
            return actor;
        }

        return actorRepository.save(actor);
    }

    @Transactional(readOnly = true)
    public Actor readByName(String name) {
        return actorRepository.findByName(name).orElseThrow(() -> new BusinessRulesException("Actor '" + name + "' not found."));
    }

    public boolean existsByName(String name) {
        return actorRepository.existsByName(name);
    }
}
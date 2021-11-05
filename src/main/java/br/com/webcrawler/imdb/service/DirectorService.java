package br.com.webcrawler.imdb.service;

import br.com.webcrawler.imdb.exception.BusinessRulesException;
import br.com.webcrawler.imdb.model.Director;
import br.com.webcrawler.imdb.repository.DirectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DirectorService {

    @Autowired
    private DirectorRepository directorRepository;

    @Transactional
    public Director save(Director director) {
        if (directorRepository.existsByName(director.getName())) {
            return director;
        }

        return directorRepository.save(director);
    }

    @Transactional(readOnly = true)
    public Director readByName(String name) {
        return directorRepository.findByName(name).orElseThrow(() -> new BusinessRulesException("Director '" + name + "' not found."));
    }

    public boolean existsByName(String name) {
        return directorRepository.existsByName(name);
    }
}
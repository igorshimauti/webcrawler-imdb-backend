package br.com.webcrawler.imdb.repository;

import br.com.webcrawler.imdb.model.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {

    boolean existsByName(String name);
    Optional<Actor> findByName(String name);
}
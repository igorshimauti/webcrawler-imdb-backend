package br.com.webcrawler.imdb.repository;

import br.com.webcrawler.imdb.model.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Integer> {

    boolean existsByName(String name);
    Optional<Director> findByName(String name);
}
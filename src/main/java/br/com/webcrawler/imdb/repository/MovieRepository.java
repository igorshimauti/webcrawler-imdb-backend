package br.com.webcrawler.imdb.repository;

import br.com.webcrawler.imdb.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

    boolean existsById(Integer id);
    boolean existsByName(String name);
    Movie findByName(String name);
    List<Movie> findTop10ByOrderByRate();
}
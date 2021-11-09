package br.com.webcrawler.imdb.service;

import br.com.webcrawler.imdb.exception.BusinessRulesException;
import br.com.webcrawler.imdb.model.Movie;
import br.com.webcrawler.imdb.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private DirectorService directorService;

    @Autowired
    private ActorService actorService;

    @Transactional
    public Movie save(Movie movie) {
        if (movie.getId() == null && movieRepository.existsByName(movie.getName())) {
            movie.setId(movieRepository.findByName(movie.getName()).getId());
        }

        /*if (movie.getComments() != null) {
            movie.getComments().forEach(comment -> {
                comment.setMovie(movie);
            });
        }*/

        return movieRepository.save(movie);
    }

    @Transactional(readOnly = true)
    public List<Movie> findTop10ByOrderByRate() {
        return movieRepository.findTop10ByOrderByRate();
    }

    @Transactional(readOnly = true)
    public Movie readById(Integer movieId) {
        return movieRepository.findById(movieId).orElseThrow(() -> new BusinessRulesException("Movie with ID '" + movieId + "' not found."));
    }

    @Transactional(readOnly = true)
    public Movie readByIdImdb(String idImdb) {
        return movieRepository.findByIdImdb(idImdb).orElseThrow(() -> new BusinessRulesException("Movie with ID IMDb '" + idImdb + "' not found."));
    }

    @Transactional
    public void deleteById(Integer movieId) {
        movieRepository.deleteById(movieId);
    }

    public boolean existsById(Integer movieId) {
        return movieRepository.existsById(movieId);
    }

    public boolean existsByIdImdb(String idImdb) {
        return movieRepository.existsByIdImdb(idImdb);
    }

    @Transactional
    public void deleteAll() {
        movieRepository.deleteAll();
    }
}
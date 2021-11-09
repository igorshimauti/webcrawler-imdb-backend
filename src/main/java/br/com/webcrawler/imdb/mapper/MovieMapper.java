package br.com.webcrawler.imdb.mapper;

import br.com.webcrawler.imdb.dto.MovieDto;
import br.com.webcrawler.imdb.model.Movie;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MovieMapper {

    @Autowired
    private ModelMapper modelMapper;

    public MovieDto toDto(Movie movie) {
        return modelMapper.map(movie, MovieDto.class);
    }

    public List<MovieDto> toCollectionDto(List<Movie> movies) {
        return movies.stream().map(this::toDto).collect(Collectors.toList());
    }

    public Movie toEntity(MovieDto movieDto) {
        return modelMapper.map(movieDto, Movie.class);
    }

    public List<Movie> toCollectionEntity(List<MovieDto> moviesDto) {
        return moviesDto.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
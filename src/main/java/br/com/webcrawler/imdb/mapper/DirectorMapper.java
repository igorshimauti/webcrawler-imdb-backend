package br.com.webcrawler.imdb.mapper;

import br.com.webcrawler.imdb.dto.DirectorDto;
import br.com.webcrawler.imdb.model.Director;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DirectorMapper {

    @Autowired
    private ModelMapper modelMapper;

    public DirectorDto toDto(Director director) {
        return modelMapper.map(director, DirectorDto.class);
    }

    public List<DirectorDto> toCollectionDto(List<Director> directors) {
        return directors.stream().map(this::toDto).collect(Collectors.toList());
    }

    public Director toEntity(DirectorDto directorDto) {
        return modelMapper.map(directorDto, Director.class);
    }

    public List<Director> toCollectionEntity(List<DirectorDto> directorsDto) {
        return directorsDto.stream().map(this::toEntity).collect(Collectors.toList());
    }
}

package br.com.webcrawler.imdb.mapper;

import br.com.webcrawler.imdb.dto.ActorDto;
import br.com.webcrawler.imdb.model.Actor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ActorMapper {

    @Autowired
    private ModelMapper modelMapper;

    public ActorDto toDto(Actor actor) {
        return modelMapper.map(actor, ActorDto.class);
    }

    public List<ActorDto> toCollectionDto(List<Actor> actors) {
        return actors.stream().map(this::toDto).collect(Collectors.toList());
    }

    public Actor toEntity(ActorDto actorDto) {
        return modelMapper.map(actorDto, Actor.class);
    }

    public List<Actor> toCollectionEntity(List<ActorDto> actorsDto) {
        return actorsDto.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
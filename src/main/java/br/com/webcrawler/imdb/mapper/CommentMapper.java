package br.com.webcrawler.imdb.mapper;

import br.com.webcrawler.imdb.dto.CommentDto;
import br.com.webcrawler.imdb.model.Comment;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommentMapper {

    @Autowired
    private ModelMapper modelMapper;

    public CommentDto toDto(Comment comment) {
        return modelMapper.map(comment, CommentDto.class);
    }

    public List<CommentDto> toCollectionDto(List<Comment> comments) {
        return comments.stream().map(this::toDto).collect(Collectors.toList());
    }

    public Comment toEntity(CommentDto commentDto) {
        return modelMapper.map(commentDto, Comment.class);
    }

    public List<Comment> toCollectionEntity(List<CommentDto> commentsDto) {
        return commentsDto.stream().map(this::toEntity).collect(Collectors.toList());
    }
}

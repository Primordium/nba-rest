package com.hro.exercise.nbachallenge.converters;

import com.hro.exercise.nbachallenge.command.CommentDto;
import com.hro.exercise.nbachallenge.persistence.model.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentToCommentDto extends AbstractConverter<Comment, CommentDto>{

    @Override
    public CommentDto convert(Comment comment) {

        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setComment(comment.getComment());
        commentDto.setDate(comment.getUpdateTime());

        return commentDto;
    }
}

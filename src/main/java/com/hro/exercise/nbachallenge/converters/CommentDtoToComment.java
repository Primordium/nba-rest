package com.hro.exercise.nbachallenge.converters;

import com.hro.exercise.nbachallenge.command.CommentDto;
import com.hro.exercise.nbachallenge.persistence.model.Comment;
import com.hro.exercise.nbachallenge.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommentDtoToComment extends AbstractConverter<CommentDto, Comment>{

    private CommentService commentService;

    @Autowired
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }

    @Override
    public Comment convert(CommentDto commentDto) {
        Comment comment = (commentDto.getId() != null ? commentService.get(commentDto.getId()) : new Comment());
        comment.setComment(commentDto.getComment());
        comment.setDate(commentDto.getDate());

        return comment;
    }
}

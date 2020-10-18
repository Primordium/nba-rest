package com.hro.exercise.nbachallenge.converters;

import com.hro.exercise.nbachallenge.command.CommentDto;
import com.hro.exercise.nbachallenge.persistence.dao.CommentRepository;
import com.hro.exercise.nbachallenge.persistence.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommentDtoToComment extends AbstractConverter<CommentDto, Comment>{

    private CommentRepository commentRepository;

    @Autowired
    public void setCommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment convert(CommentDto commentDto) {
        Comment comment = (commentDto.getId() != null ? commentRepository.getOne(commentDto.getId()) : new Comment());
        comment.setComment(commentDto.getComment());
        comment.setDate(commentDto.getDate());

        return comment;
    }
}

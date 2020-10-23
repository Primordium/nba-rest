package com.hro.exercise.nbachallenge.converters;

import com.hro.exercise.nbachallenge.command.CommentDto;
import com.hro.exercise.nbachallenge.persistence.dao.CommentRepository;
import com.hro.exercise.nbachallenge.persistence.model.Comment;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CommentDtoToCommentTest {


    @MockBean
    private CommentRepository commentRepository = mock(CommentRepository.class);

    @MockBean
    private AbstractConverter abstractConverter;

    @Test
    void convert() {

        int fakeId = 10000;
        String commentary = "a comment";
        Date date = new Date();

        //Setup
        CommentDtoToComment commentDtoToComment = new CommentDtoToComment();
        commentDtoToComment.setCommentService(commentRepository);
        Comment fakeComment = new Comment();

        CommentDto commentDto = new CommentDto();
        commentDto.setId(fakeId);
        commentDto.setComment(commentary);
        commentDto.setDate(date);

        fakeComment.setId(fakeId);

        when(commentRepository.getOne(anyInt())).thenReturn(fakeComment);
        Comment comment = commentDtoToComment.convert(commentDto);

        verify(commentRepository, times(1)).getOne(anyInt());

        assertEquals(comment.getDate(), fakeComment.getDate());
        assertEquals(comment.getComment(), fakeComment.getComment());


    }
}
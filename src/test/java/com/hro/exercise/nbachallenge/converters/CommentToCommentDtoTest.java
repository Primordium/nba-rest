package com.hro.exercise.nbachallenge.converters;

import com.hro.exercise.nbachallenge.persistence.model.Game;
import org.junit.jupiter.api.Test;
import com.hro.exercise.nbachallenge.command.CommentDto;
import com.hro.exercise.nbachallenge.persistence.dao.CommentRepository;
import com.hro.exercise.nbachallenge.persistence.model.Comment;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;

class CommentToCommentDtoTest {

    @MockBean
    private CommentRepository commentRepository = mock(CommentRepository.class);

    @MockBean
    private AbstractConverter abstractConverter;

    @Test
    void convert() {
        Game game = new Game();
        String sentence = "a comment";
        Comment comment = new Comment();
        Date date = new Date();
        Integer id = 12;
        //Setup
        comment.setGame(game);
        comment.setId(12);
        comment.setComment(sentence);
        comment.setDate(date);

        CommentToCommentDto commentToCommentDto = new CommentToCommentDto();

        CommentDto commentDto = commentToCommentDto.convert(comment);

        assertTrue(commentDto.getId().equals(id));
        assertTrue(commentDto.getComment().equals(sentence));


    }
}
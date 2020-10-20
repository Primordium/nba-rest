package com.hro.exercise.nbachallenge.controller.rest;

import com.hro.exercise.nbachallenge.converters.GameDtoToGame;
import com.hro.exercise.nbachallenge.converters.GameToGameDto;
import com.hro.exercise.nbachallenge.converters.PlayerScoresDtoToPlayerScores;
import com.hro.exercise.nbachallenge.converters.PlayerScoresToPlayerScoresDto;
import com.hro.exercise.nbachallenge.persistence.dao.CommentRepository;
import com.hro.exercise.nbachallenge.persistence.dao.GameRepository;
import com.hro.exercise.nbachallenge.persistence.model.Comment;
import com.hro.exercise.nbachallenge.persistence.model.Game;
import com.hro.exercise.nbachallenge.util.RapidApiConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest
class RestCommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Comment comment;
    @MockBean
    private CommentRepository commentRepository;
    @MockBean
    private GameRepository gameRepository;
    @MockBean
    private Game game;
    @MockBean
    private GameToGameDto gameToGameDto;
    @MockBean
    private GameDtoToGame gameDtoToGame;
    @MockBean
    private PlayerScoresDtoToPlayerScores playerScoresDtoToPlayerScores;
    @MockBean
    private PlayerScoresToPlayerScoresDto playerScoresToPlayerScoresDto;
    @MockBean
    private RapidApiConnection rapidApiConnection;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void putCommentsForGameIdWithInvalidId() throws Exception {
        int invalidId = 888;
        String commentSentence = "fake comment";
        when(gameRepository.findByGameId(invalidId)).thenReturn(null);
        mockMvc.perform(put("/api/nbadb/comments/edit/{gameId}", invalidId)
                .accept(MediaType.TEXT_PLAIN)
                .contentType(MediaType.TEXT_PLAIN)
                .content(commentSentence))
                .andExpect(status().isNotFound());
        verify(commentRepository, times(1)).findById(invalidId);
    }

    @Test
    void putCommentsForGameIdWithValidId() throws Exception {
        int validId = 888;
        String commentSentence = "fake comment";

        when(commentRepository.findById(validId)).thenReturn(Optional.of(comment));
        when(commentRepository.getOne(validId)).thenReturn(comment);
        when(gameRepository.findByGameId(validId)).thenReturn(game);
        when(comment.getGame()).thenReturn(game);

        mockMvc.perform(put("/api/nbadb/comments/edit/{gameId}", validId)
                .accept(MediaType.TEXT_PLAIN)
                .contentType(MediaType.TEXT_PLAIN)
                .content(commentSentence))
                .andExpect(status().isOk());
        verify(comment, times(1)).editComment(commentSentence);
        verify(gameRepository, times(1)).save(game);
    }

    @Test
    void postCommentsForGameIdWithInvalidId() throws Exception {
        int invalidId = 888;
        String comment = "aaahahah";

        when(gameRepository.findByGameId(invalidId)).thenReturn(null);
        mockMvc.perform(post("/api/nbadb/comments/{gameId}", invalidId)
                .accept(MediaType.TEXT_PLAIN)
                .contentType(MediaType.TEXT_PLAIN)
                .content(comment))
                .andExpect(status().isNotFound());
        verify(gameRepository, times(1)).findByGameId(invalidId);
    }


    @Test
    void postCommentsForGameIdWithValidId() throws Exception {
        int validId = 888;
        String comment = "aaahahah";

        when(gameRepository.findByGameId(validId)).thenReturn(game);
        mockMvc.perform(post("/api/nbadb/comments/{gameId}", validId)
                .accept(MediaType.TEXT_PLAIN)
                .contentType(MediaType.TEXT_PLAIN)
                .content(comment))
                .andExpect(status().isCreated());
        verify(gameRepository, times(1)).findByGameId(validId);
        verify(gameRepository, times(1)).save(game);
    }


    @Test
    void deleteCommentByIdWithInvalidId() throws Exception {
        int invalidId = 888;
        when(commentRepository.findById(invalidId)).thenReturn(Optional.empty());
        mockMvc.perform(delete("/api/nbadb/comments/{commentId}", invalidId))
                .andExpect(status()
                        .isNotFound());
        verify(commentRepository, times(1)).findById(invalidId);
    }

    @Test
    void deleteCommentByIdWithValidId() throws Exception {
        Integer commentId = 888;

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
        when(comment.getGame()).thenReturn(game);

        when(commentRepository.getOne(commentId)).thenReturn(comment);
        when(comment.getGame()).thenReturn(game);
        mockMvc.perform(delete("/api/nbadb/comments/{commentId}", commentId))
                .andExpect(status()
                        .isOk());
        verify(gameRepository, times(1)).save(game);
    }
}
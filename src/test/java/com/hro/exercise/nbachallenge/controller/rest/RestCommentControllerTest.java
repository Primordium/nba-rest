package com.hro.exercise.nbachallenge.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hro.exercise.nbachallenge.converters.GameDtoToGame;
import com.hro.exercise.nbachallenge.converters.GameToGameDto;
import com.hro.exercise.nbachallenge.converters.PlayerScoresDtoToPlayerScores;
import com.hro.exercise.nbachallenge.converters.PlayerScoresToPlayerScoresDto;
import com.hro.exercise.nbachallenge.persistence.dao.CommentRepository;
import com.hro.exercise.nbachallenge.persistence.dao.GameRepository;
import com.hro.exercise.nbachallenge.persistence.model.Comment;
import com.hro.exercise.nbachallenge.persistence.model.Game;
import com.hro.exercise.nbachallenge.util.RapidApiConnection;
import net.bytebuddy.pool.TypePool;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest
class RestCommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CommentRepository commentRepository;

    @MockBean
    private GameRepository gameRepository;
    @MockBean
    private Comment comment;
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
        objectMapper = new ObjectMapper();
        comment = new Comment();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void putCommentsForGameIdWithInvalidId() throws Exception {
        int invalidId = 888;
        String comment = "aaahahah";
        when(gameRepository.findByGameId(invalidId)).thenReturn(null);
        mockMvc.perform(put("/comments/edit/{gameId}", invalidId)
                .accept(MediaType.TEXT_PLAIN)
                .contentType(MediaType.TEXT_PLAIN)
                .content(comment))
                .andExpect(status().isNotFound());

    }

    @Test
    void postCommentsForGameIdWithInvalidId() throws Exception {
        int invalidId = 888;
        String comment = "aaahahah";

        when(gameRepository.findByGameId(invalidId)).thenReturn(null);
        mockMvc.perform(post("/comments/{gameId}", invalidId)
                .accept(MediaType.TEXT_PLAIN)
                .contentType(MediaType.TEXT_PLAIN)
                .content(comment))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteCommentByIdWithInvalidId() throws Exception {
        int invalidId = 888;
        when(commentRepository.findById(invalidId)).thenReturn(Optional.empty());
        mockMvc.perform(post("/comments/{commentId}", invalidId))
                .andExpect(status()
                        .isNotFound());
    }

    @Test
    void deleteCommentByIdWithValidId() throws Exception {
        int validId = 888;
        int gameId = 555;
        Comment commentTest = new Comment("aaa");

        when(commentRepository.findById(validId)).thenReturn(Optional.of(commentTest));
        when(commentRepository.getOne(validId)).thenReturn(comment);

        mockMvc.perform(delete("/comments/{validId}", validId))
                .andExpect(status().isOk());

        verify(commentRepository, times(1)).save(ArgumentMatchers.any(Comment.class));
    }
}
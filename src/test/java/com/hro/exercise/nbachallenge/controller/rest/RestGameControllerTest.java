package com.hro.exercise.nbachallenge.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hro.exercise.nbachallenge.command.GameDto;
import com.hro.exercise.nbachallenge.converters.GameDtoToGame;
import com.hro.exercise.nbachallenge.converters.GameToGameDto;
import com.hro.exercise.nbachallenge.converters.PlayerScoresDtoToPlayerScores;
import com.hro.exercise.nbachallenge.converters.PlayerScoresToPlayerScoresDto;
import com.hro.exercise.nbachallenge.persistence.dao.CommentRepository;
import com.hro.exercise.nbachallenge.persistence.dao.GameRepository;
import com.hro.exercise.nbachallenge.persistence.model.Game;
import com.hro.exercise.nbachallenge.util.RapidApiConnection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.awt.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@WebMvcTest
class RestGameControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CommentRepository commentRepository;
    @MockBean
    private GameRepository gameRepository;
    @MockBean
    private RestGameController restGameController;
    @MockBean
    private GameDtoToGame gameDtoToGame;
    @MockBean
    private GameToGameDto gameToGameDto;
    @MockBean
    private RapidApiConnection rapidApiConnection;
    @MockBean
    private PlayerScoresToPlayerScoresDto playerScoresToPlayerScoresDto;
    @MockBean
    private PlayerScoresDtoToPlayerScores playerScoresDtoToPlayerScores;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.initMocks(this);
    }

    @AfterEach
    void tearDown() {
    }


    @Test
    void usageInstructions() throws Exception {
        this.mockMvc.perform(get("http://localhost:8080/api/nbadb/")).andExpect(status().isOk());
    }

    @Test
    void getGamesByDateValidDate() throws Exception {
        String validDate = "1980-03-40";
        List<Game> gameList = new LinkedList<>();
        gameList.add(new Game());
        List<GameDto> gameDtoList= new LinkedList<>();
        gameDtoList.add(new GameDto());

        when(gameRepository.findByGameDate(any())).thenReturn(gameList);
        when(rapidApiConnection.getGamesByDate(validDate)).thenReturn(gameDtoList);
        this.mockMvc.perform(get("http://localhost:8080/api/nbadb/{date}", validDate)).andExpect(status().isOk());
    }

    @Test
    void getGameById() {
    }

    @Test
    void getGamesByDateWithPath() {
    }

    @Test
    void getGameByIdWithPath() {
    }
}
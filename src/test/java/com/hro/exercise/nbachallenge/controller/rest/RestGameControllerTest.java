package com.hro.exercise.nbachallenge.controller.rest;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hro.exercise.nbachallenge.command.CommentDto;
import com.hro.exercise.nbachallenge.command.GameDto;
import com.hro.exercise.nbachallenge.command.PlayerScoresDto;
import com.hro.exercise.nbachallenge.converters.GameDtoToGame;
import com.hro.exercise.nbachallenge.converters.GameToGameDto;
import com.hro.exercise.nbachallenge.converters.PlayerScoresDtoToPlayerScores;
import com.hro.exercise.nbachallenge.converters.PlayerScoresToPlayerScoresDto;
import com.hro.exercise.nbachallenge.persistence.dao.CommentRepository;
import com.hro.exercise.nbachallenge.persistence.dao.GameRepository;
import com.hro.exercise.nbachallenge.persistence.model.Game;
import com.hro.exercise.nbachallenge.persistence.model.PlayerScores;
import com.hro.exercise.nbachallenge.util.RapidApiConnection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@WebMvcTest
class RestGameControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private Game game;
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
        mockMvc.perform(get("/api/nbadb/")).andExpect(status().isOk());
    }

    @Test
    void getGamesByDateValidDate() throws Exception {
        String validDate = "1980-03-20";

        List<GameDto> gameDto = new ArrayList<>();
        List<Game> dbList = new ArrayList<>();
        dbList.add(game);

        when(gameRepository.findByGameDate(any(Date.class))).thenReturn(dbList);
        when(rapidApiConnection.getGamesByDate(validDate)).thenReturn(null);
        when(gameToGameDto.convert(anyList())).thenReturn(gameDto);

        mockMvc.perform(get("/api/nbadb/date/{date}", validDate)).andExpect(status().isOk());
    }

    @Test
    void getGamesByDateInvalidDate() throws Exception {
        String validDate = "1980111222-03-201123";
        mockMvc.perform(get("/api/nbadb/date/{date}", validDate)).andExpect(status().isBadRequest());
    }

    @Test
    void getGameById() throws Exception {
        Game game = new Game();
        int gameId = 1000;
        GameDto gameDto = new GameDto();
        gameDto.setGameId(1);
        gameDto.setComments(new LinkedList<CommentDto>());
        gameDto.setVisitorTeamName("Mini");
        gameDto.setHomeTeamName("Mi");
        gameDto.setHomeTeamScore(100);
        gameDto.setVisitorTeamScore(200);
        gameDto.setGameDate(new Date());
        gameDto.setPlayerScores(new LinkedList<PlayerScoresDto>());
        gameDto.setId(100);

        when(gameRepository.findByGameId(gameId)).thenReturn(game);
        when(gameToGameDto.convert(game)).thenReturn(gameDto);


        mockMvc.perform(get("/api/nbadb/game/{gameId}", gameId))
                .andExpect(status().isOk());

    }

    @Test
    void getGameByIdWithPath() {
    }
}
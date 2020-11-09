package com.hro.exercise.nbachallenge.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hro.exercise.nbachallenge.command.GameDto;
import com.hro.exercise.nbachallenge.command.PlayerScoresDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RapidApiConnectionTest {

    @Mock
    private NbaApiParser nbaApiParser;
    @InjectMocks
    private RapidApiConnection rapidApiConnection;
    @MockBean
    private HttpRequest httpRequest = mock(HttpRequest.class);
    @MockBean
    private HttpClient httpClient = mock(HttpClient.class);
    @MockBean
    private Path path = mock(Path.class);
    @MockBean
    private GameDto gameDto = mock(GameDto.class);
    private MockMvc mock;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mock = MockMvcBuilders.standaloneSetup(rapidApiConnection).build();
        objectMapper = new ObjectMapper();
    }
    @Test
    void getGameByIdValidId() throws IOException, ParseException {

        List<PlayerScoresDto> playerScores = new ArrayList<>();
        Date date = new Date();
        Integer gameId = 1;
        String homeTeamName = "Mini";
        String visitorTeamName = "Mi";
        Integer homeTeamScore = 100;
        Integer visitorTeamScore = 200;

        GameDto gameP = new GameDto();
        gameP.setGameId(gameId);
        gameP.setGameDate(date);
        gameP.setVisitorTeamScore(visitorTeamScore);
        gameP.setHomeTeamScore(homeTeamScore);
        gameP.setHomeTeamName(homeTeamName);
        gameP.setVisitorTeamName(visitorTeamName);
        gameP.setPlayerScores(playerScores);

        when(nbaApiParser.getGameDate(any())).thenReturn(date);
        when(nbaApiParser.getPlayerScores(any())).thenReturn(playerScores);
        when(nbaApiParser.getAllGameProperties(any(), any())).thenReturn(gameP);


        GameDto gameDto = rapidApiConnection.getGameById(1);

        assertTrue(gameDto.getGameId().equals(gameId));
        assertTrue(gameDto.getGameDate().equals(date));
        assertTrue(gameDto.getHomeTeamName().equals(homeTeamName));
        assertTrue(gameDto.getVisitorTeamName().equals(visitorTeamName));
        assertTrue(gameDto.getHomeTeamScore().equals(homeTeamScore));
        assertTrue(gameDto.getVisitorTeamScore().equals(visitorTeamScore));
        assertTrue(gameDto.getPlayerScores().equals(playerScores));

    }

}
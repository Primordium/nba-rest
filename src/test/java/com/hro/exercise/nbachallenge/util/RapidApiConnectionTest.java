package com.hro.exercise.nbachallenge.util;

import com.hro.exercise.nbachallenge.command.GameDto;
import com.hro.exercise.nbachallenge.command.PlayerScoresDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class RapidApiConnectionTest {

    @MockBean
    private HttpRequest httpRequest = mock(HttpRequest.class);
    @MockBean
    private HttpClient httpClient = mock(HttpClient.class);
    @MockBean
    private Path path = mock(Path.class);
    @MockBean
    private GameDto gameDto = mock(GameDto.class);


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void getGameById() throws IOException, ParseException {

        // Mock on nbaApiParser start to fail, was requesting real thing

        NbaApiParser nbaApiParser = mock(NbaApiParser.class);
        List<PlayerScoresDto> playerScores = new ArrayList<>();
        RapidApiConnection rapidApiConnection = new RapidApiConnection();

        GameDto gameDto = rapidApiConnection.getGameById(1);

        assertEquals(gameDto.getGameId(), 1);
        assertEquals(gameDto.getHomeTeamName(), "Boston Celtics");
        assertEquals(gameDto.getVisitorTeamName(), "Philadelphia 76ers");
        assertEquals(gameDto.getHomeTeamScore(), 105);
        assertEquals(gameDto.getVisitorTeamScore(), 87);

    }

}
package com.hro.exercise.nbachallenge.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hro.exercise.nbachallenge.command.GameDto;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.net.http.HttpResponse;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class RapidApiConnectionTest {
    @Mock
    private NbaApiParser nbaApiParser;
    @InjectMocks
    private RapidApiConnection rapidApiConnection;

    private MockMvc mock;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mock = MockMvcBuilders.standaloneSetup(rapidApiConnection).build();
        objectMapper = new ObjectMapper();

    }

    @AfterEach
    void tearDown() {

    }



    @Test
    void getGamesByDate() throws JsonProcessingException {
        HttpResponse httpResponse = null;
        List<GameDto> gameDtoList = new LinkedList<>();
        when(nbaApiParser.getAllStatsByDate(httpResponse)).thenReturn(gameDtoList);
    }

    @Test
    void getGameById() {
    }
}
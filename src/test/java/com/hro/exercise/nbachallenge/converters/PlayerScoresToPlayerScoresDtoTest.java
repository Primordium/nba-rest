package com.hro.exercise.nbachallenge.converters;

import com.hro.exercise.nbachallenge.command.PlayerScoresDto;
import com.hro.exercise.nbachallenge.persistence.dao.GameRepository;
import com.hro.exercise.nbachallenge.persistence.dao.PlayerScoresRepository;
import com.hro.exercise.nbachallenge.persistence.model.PlayerScores;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class PlayerScoresToPlayerScoresDtoTest {


    @MockBean
    private GameRepository gameRepository = mock(GameRepository.class);
    @MockBean
    private PlayerScoresRepository playerScoresRepository = mock(PlayerScoresRepository.class);

    @MockBean
    private AbstractConverter abstractConverter;

    @Test
    void convert() {

        int id = 10;
        Integer score = 100;
        String firstName = "Mini";
        String lastName = "Mi";

        //Setup
        PlayerScores playerScores = new PlayerScores();
        playerScores.setScore(score);
        playerScores.setFirstName(firstName);
        playerScores.setLastName(lastName);
        playerScores.setId(10);

        PlayerScoresToPlayerScoresDto playerScoresToPlayerScoresDto = new PlayerScoresToPlayerScoresDto();

        PlayerScoresDto playerScoresDto = playerScoresToPlayerScoresDto.convert(playerScores);

        assertEquals(playerScoresDto.getFirstName(), firstName);
        assertEquals(playerScoresDto.getLastName(), lastName);
        assertEquals(playerScoresDto.getScore(), score);
        assertEquals((int) playerScoresDto.getId(), id);


    }
}
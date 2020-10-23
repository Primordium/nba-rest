package com.hro.exercise.nbachallenge.converters;

import com.hro.exercise.nbachallenge.command.PlayerScoresDto;
import com.hro.exercise.nbachallenge.persistence.dao.GameRepository;
import com.hro.exercise.nbachallenge.persistence.dao.PlayerScoresRepository;
import com.hro.exercise.nbachallenge.persistence.model.PlayerScores;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PlayerScoresDtoToPlayerScoresTest {


    @MockBean
    private GameRepository gameRepository = mock(GameRepository.class);
    @MockBean
    private PlayerScoresRepository playerScoresRepository = mock(PlayerScoresRepository.class);

    @MockBean
    private AbstractConverter abstractConverter;

    @Test
    void convert() {

        Integer score = 100;
        String firstName = "Mini";
        String lastName = "Mi";

        //Setup
        PlayerScoresDto playerScoresDto = new PlayerScoresDto();
        playerScoresDto.setScore(score);
        playerScoresDto.setFirstName(firstName);
        playerScoresDto.setLastName(lastName);

        PlayerScoresDtoToPlayerScores playerScoresDtoToPlayerScores = new PlayerScoresDtoToPlayerScores();
        playerScoresDtoToPlayerScores.setPlayerScoresRepository(playerScoresRepository);


        when(playerScoresRepository.getOne(anyInt())).thenReturn(null);

        PlayerScores playerScores = playerScoresDtoToPlayerScores.convert(playerScoresDto);

        assertEquals(playerScores.getFirstName(), firstName);
        assertEquals(playerScores.getLastName(), lastName);
        assertEquals(playerScores.getScore(), score);

    }
}
package com.hro.exercise.nbachallenge.converters;

import com.hro.exercise.nbachallenge.command.PlayerScoresDto;
import com.hro.exercise.nbachallenge.persistence.dao.GameRepository;
import com.hro.exercise.nbachallenge.persistence.dao.PlayerScoresRepository;
import com.hro.exercise.nbachallenge.persistence.model.PlayerScores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * A {@link Converter} implementation, responsible for {@link PlayerScoresDto} to {@link PlayerScores} type conversion
 */

@Component
public class PlayerScoresDtoToPlayerScores extends AbstractConverter<PlayerScoresDto, PlayerScores> {

    private PlayerScoresRepository playerScoresRepository;

    @Autowired
    public void setPlayerScoresRepository(PlayerScoresRepository playerScoresRepository) {
        this.playerScoresRepository = playerScoresRepository;
    }

    @Override
    public PlayerScores convert(PlayerScoresDto playerScoresDto) {
        PlayerScores playerScores = (playerScoresDto.getId() != null ?
                playerScoresRepository.getOne(playerScoresDto.getId()) : new PlayerScores());

        playerScores.setLastName(playerScoresDto.getLastName());
        playerScores.setFirstName(playerScoresDto.getFirstName());
        playerScores.setScore(playerScoresDto.getScore());

        return playerScores;
    }
}

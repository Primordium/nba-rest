package com.hro.exercise.nbachallenge.converters;

import com.hro.exercise.nbachallenge.command.PlayerScoresDto;
import com.hro.exercise.nbachallenge.persistence.model.PlayerScores;
import org.springframework.stereotype.Component;

@Component
public class PlayerScoresToPlayerScoresDto extends AbstractConverter<PlayerScores, PlayerScoresDto>{

    @Override
    public PlayerScoresDto convert(PlayerScores playerScores) {
        PlayerScoresDto playerScoresDto = new PlayerScoresDto();
        playerScoresDto.setFirstName(playerScores.getFirstName());
        playerScoresDto.setLastName(playerScores.getLastName());
        playerScoresDto.setId(playerScores.getId());
        playerScoresDto.setScore(playerScores.getScore());

        return playerScoresDto;
    }
}

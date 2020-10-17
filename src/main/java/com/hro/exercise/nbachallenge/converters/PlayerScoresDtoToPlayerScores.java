package com.hro.exercise.nbachallenge.converters;

import com.hro.exercise.nbachallenge.command.PlayerScoresDto;
import com.hro.exercise.nbachallenge.persistence.model.PlayerScores;
import com.hro.exercise.nbachallenge.service.PlayerScoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlayerScoresDtoToPlayerScores extends AbstractConverter<PlayerScoresDto, PlayerScores>{

    private PlayerScoresService playerScoresService;

    @Autowired
    public void setPlayerScoresService(PlayerScoresService playerScoresService) {
        this.playerScoresService = playerScoresService;
    }

    @Override
    public PlayerScores convert(PlayerScoresDto playerScoresDto) {

        PlayerScores playerScores = (playerScoresDto.getId() != null ? playerScoresService.get(playerScoresDto.getId()) : new PlayerScores());
        playerScores.setLastName(playerScoresDto.getLastName());
        playerScores.setFirstName(playerScoresDto.getFirstName());
        playerScores.setScore(playerScoresDto.getScore());

        return playerScores;
    }
}

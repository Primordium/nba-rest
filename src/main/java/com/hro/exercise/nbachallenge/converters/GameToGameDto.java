package com.hro.exercise.nbachallenge.converters;

import com.hro.exercise.nbachallenge.command.GameDto;
import com.hro.exercise.nbachallenge.command.PlayerDto;
import com.hro.exercise.nbachallenge.persistence.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class GameToGameDto extends AbstractConverter<Game, GameDto>{
    private CommentToCommentDto commentToCommentDto;
    private PlayerToPlayerDto playerToPlayerDto;

    @Autowired
    public void setCommentToCommentDto(CommentToCommentDto commentToCommentDto) {
        this.commentToCommentDto = commentToCommentDto;
    }

    @Autowired
    public void setPlayerDtoToPlayer(PlayerToPlayerDto playerDtoToPlayer) {
        this.playerToPlayerDto = playerDtoToPlayer;
    }

    @Override
    public GameDto convert(Game game) {

        GameDto gameDto = new GameDto();
        gameDto.setId(game.getId());
        gameDto.setGameId(game.getGameId());
        gameDto.setHomeTeamName(game.getHomeTeamName());
        gameDto.setVisitorTeamName(game.getVisitorTeamName());
        gameDto.setGameDate(game.getGameDate());

        Map<PlayerDto, Integer> playerAndScores = new HashMap<>();

        game.getPlayerScores().forEach((player, score) -> {
            PlayerDto playerDto = playerToPlayerDto.convert(player);
            Integer points = score.intValue();
            playerAndScores.put(playerDto, points);
        });

        gameDto.setComments(commentToCommentDto.convert(game.getCommentList()));

        return gameDto;
    }
}

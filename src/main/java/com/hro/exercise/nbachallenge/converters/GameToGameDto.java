package com.hro.exercise.nbachallenge.converters;

import com.hro.exercise.nbachallenge.command.GameDto;
import com.hro.exercise.nbachallenge.persistence.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class GameToGameDto extends AbstractConverter<Game, GameDto>{
    private CommentToCommentDto commentToCommentDto;

    @Autowired
    public void setCommentToCommentDto(CommentToCommentDto commentToCommentDto) {
        this.commentToCommentDto = commentToCommentDto;
    }

    @Override
    public GameDto convert(Game game) {

        GameDto gameDto = new GameDto();
        gameDto.setId(game.getId());
        gameDto.setGameId(game.getGameId());
        gameDto.setHomeTeamName(game.getHomeTeamName());
        gameDto.setVisitorTeamName(game.getVisitorTeamName());
        gameDto.setGameDate(game.getGameDate());

        Map<String, Integer> playerAndScores = new HashMap<>();

        game.getPlayerScores().forEach((player, score) -> {
            String name = player.getLastName() + ", " + player.getFirstName();
            Integer points = score.intValue();
            playerAndScores.put(name, points);
        });

        gameDto.setComments(commentToCommentDto.convert(game.getCommentList()));




        return null;
    }
}

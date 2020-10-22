package com.hro.exercise.nbachallenge.converters;

import com.hro.exercise.nbachallenge.command.GameDto;
import com.hro.exercise.nbachallenge.persistence.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * A {@link Converter} implementation, responsible for {@link Game} to {@link GameDto} type conversion
 */
@Component
public class GameToGameDto extends AbstractConverter<Game, GameDto> {

    private CommentToCommentDto commentToCommentDto;
    private PlayerScoresToPlayerScoresDto playerScoresToPlayerScoresDto;

    @Autowired
    public void setCommentToCommentDto(CommentToCommentDto commentToCommentDto) {
        this.commentToCommentDto = commentToCommentDto;
    }

    @Autowired
    public void setPlayerDtoToPlayer(PlayerScoresToPlayerScoresDto playerScoresToPlayerScoresDto) {
        this.playerScoresToPlayerScoresDto = playerScoresToPlayerScoresDto;
    }

    @Override
    public GameDto convert(Game game) {
        GameDto gameDto = new GameDto();

        gameDto.setId(game.getId());
        gameDto.setGameId(game.getGameId());
        gameDto.setHomeTeamName(game.getHomeTeamName());
        gameDto.setVisitorTeamName(game.getVisitorTeamName());
        gameDto.setHomeTeamScore(game.getHomeTeamScore());
        gameDto.setVisitorTeamScore(game.getVisitorTeamScore());
        gameDto.setGameDate(game.getGameDate());

        gameDto.setPlayerScores(playerScoresToPlayerScoresDto.convert(game.getPlayerScores()));
        gameDto.setComments(commentToCommentDto.convert(game.getCommentList()));

        return gameDto;
    }
}

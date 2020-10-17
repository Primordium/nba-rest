package com.hro.exercise.nbachallenge.converters;

import com.hro.exercise.nbachallenge.command.GameDto;
import com.hro.exercise.nbachallenge.persistence.model.Game;
import com.hro.exercise.nbachallenge.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameDtoToGame extends AbstractConverter<GameDto, Game>{

    private GameService gameService;
    private CommentDtoToComment commentDtoToComment;

    @Autowired
    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }
    @Autowired
    public void setCommentDtoToComment(CommentDtoToComment commentDtoToComment) {
        this.commentDtoToComment = commentDtoToComment;
    }

    @Override
    public Game convert(GameDto gameDto) {
        Game game = (gameDto.getId() != null ? gameService.get(gameDto.getId()) : new Game());
        game.setCommentList(commentDtoToComment.convert(gameDto.getComments()));
        game.setVisitorTeamName(gameDto.getVisitorTeamName());
        game.setHomeTeamName(gameDto.getHomeTeamName());
        game.setHomeTeamScore(gameDto.getHomeTeamScore());
        game.setVisitorTeamScore(gameDto.getVisitorTeamScore());
        game.setGameDate(gameDto.getGameDate());

        return null;
    }
}

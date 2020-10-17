package com.hro.exercise.nbachallenge.converters;

import com.hro.exercise.nbachallenge.command.GameDto;
import com.hro.exercise.nbachallenge.persistence.model.Comment;
import com.hro.exercise.nbachallenge.persistence.model.Game;
import com.hro.exercise.nbachallenge.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class GameDtoToGame extends AbstractConverter<GameDto, Game>{

    private GameService gameService;
    private CommentDtoToComment commentDtoToComment;
    private PlayerScoresDtoToPlayerScores playerScoresDtoToPlayerScores;

    @Autowired
    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }
    @Autowired
    public void setCommentDtoToComment(CommentDtoToComment commentDtoToComment) {
        this.commentDtoToComment = commentDtoToComment;
    }
    @Autowired
    public void setPlayerScoresDtoToPlayerScores(PlayerScoresDtoToPlayerScores playerScoresDtoToPlayerScores) {
        this.playerScoresDtoToPlayerScores = playerScoresDtoToPlayerScores;
    }

    @Override
    public Game convert(GameDto gameDto) {
        Game game = (gameDto.getId() != null ? gameService.get(gameDto.getId()) : new Game());

        game.setVisitorTeamName(gameDto.getVisitorTeamName());
        game.setHomeTeamName(gameDto.getHomeTeamName());
        game.setHomeTeamScore(gameDto.getHomeTeamScore());
        game.setVisitorTeamScore(gameDto.getVisitorTeamScore());
        game.setGameDate(gameDto.getGameDate());
        game.setGameId(gameDto.getGameId());

        game.setPlayerScores(playerScoresDtoToPlayerScores.convert(gameDto.getPlayerScores()));

        if(gameDto.getComments() != null) {
        game.setCommentList(commentDtoToComment.convert(gameDto.getComments()));
        }else{
            game.setCommentList(new ArrayList<Comment>());
        }
        return game;
    }
}

package com.hro.exercise.nbachallenge.converters;

import com.hro.exercise.nbachallenge.command.GameDto;
import com.hro.exercise.nbachallenge.persistence.model.Comment;
import com.hro.exercise.nbachallenge.persistence.model.Game;
import com.hro.exercise.nbachallenge.persistence.model.Player;
import com.hro.exercise.nbachallenge.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

@Component
public class GameDtoToGame extends AbstractConverter<GameDto, Game>{

    private GameService gameService;
    private CommentDtoToComment commentDtoToComment;
    private PlayerDtoToPlayer playerDtoToPlayer;

    @Autowired
    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }
    @Autowired
    public void setCommentDtoToComment(CommentDtoToComment commentDtoToComment) {
        this.commentDtoToComment = commentDtoToComment;
    }
    @Autowired
    public void setPlayerDtoToPlayer(PlayerDtoToPlayer playerDtoToPlayer) {
        this.playerDtoToPlayer = playerDtoToPlayer;
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

        Map<Player, Integer> playerAndScores = new HashMap<>();

        System.out.println("test");
        System.out.println(gameDto.getPlayerScores());
        gameDto.getPlayerScores().forEach((playerDto, score) -> {
            Player player = playerDtoToPlayer.convert(playerDto);
            Integer points = score.intValue();
            playerAndScores.put(player, points);
        });


        if(gameDto.getComments() != null) {
        game.setCommentList(commentDtoToComment.convert(gameDto.getComments()));
        }else{
            game.setCommentList(new ArrayList<Comment>());
        }

        System.out.println(game.getPlayerScores());
        return game;
    }
}

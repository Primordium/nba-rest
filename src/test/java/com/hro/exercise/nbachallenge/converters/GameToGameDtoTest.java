package com.hro.exercise.nbachallenge.converters;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.hro.exercise.nbachallenge.command.GameDto;
import com.hro.exercise.nbachallenge.command.PlayerScoresDto;
import com.hro.exercise.nbachallenge.persistence.dao.GameRepository;
import com.hro.exercise.nbachallenge.persistence.model.Game;
import com.hro.exercise.nbachallenge.persistence.model.PlayerScores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.hro.exercise.nbachallenge.command.CommentDto;
import com.hro.exercise.nbachallenge.persistence.model.Comment;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

class GameToGameDtoTest {

    @MockBean
    private GameRepository gameRepository = mock(GameRepository.class);
    @MockBean
    @Autowired
    private PlayerScoresToPlayerScoresDto playerScoresToPlayerScoresDto = mock(PlayerScoresToPlayerScoresDto.class);
    @MockBean
    private CommentToCommentDto commentToCommentDto = mock(CommentToCommentDto.class);
    @MockBean
    private AbstractConverter abstractConverter = mock(AbstractConverter.class);


    @Test
    void convert() {
        GameToGameDto gameToGameDto = new GameToGameDto();
        gameToGameDto.setCommentToCommentDto(commentToCommentDto);
        gameToGameDto.setPlayerDtoToPlayer(playerScoresToPlayerScoresDto);

        Game game = new Game();
        List<PlayerScoresDto> playerScoresDtoList = new LinkedList<>();
        playerScoresDtoList.add(new PlayerScoresDto());
        List<PlayerScores> playerScores = new LinkedList<>();
        List<CommentDto> commentDtoList = new LinkedList<>();
        List<Comment> comments = new LinkedList<>();
        Date date = new Date();
        Integer gameId = 100;
        Integer score = 10;
        String homeTeamName = "home team";
        String visitorTeamName = "visitor team";

        game.setId(20);
        game.setPlayerScores(playerScores);
        game.setGameDate(date);
        game.setVisitorTeamScore(score);
        game.setHomeTeamScore(score);
        game.setHomeTeamName(homeTeamName);
        game.setVisitorTeamName(visitorTeamName);
        game.setGameId(100);
        game.setCommentList(comments);

        when(playerScoresToPlayerScoresDto.convert(anyList())).thenReturn(playerScoresDtoList);
        when(commentToCommentDto.convert(anyList())).thenReturn(commentDtoList);
        when(gameRepository.getOne(anyInt())).thenReturn(new Game());

        GameDto gameDto = gameToGameDto.convert(game);

        assertTrue(game.getGameDate().equals(gameDto.getGameDate()));
        assertTrue(game.getCommentList().equals(comments));
        assertTrue(game.getPlayerScores().equals(playerScores));
        assertTrue(game.getGameId().equals(gameId));
        assertTrue(game.getHomeTeamName().equals(homeTeamName));
        assertTrue(game.getVisitorTeamName().equals(visitorTeamName));
        assertTrue(game.getHomeTeamScore().equals(score));
        assertTrue(game.getVisitorTeamScore().equals(score));

        verify(playerScoresToPlayerScoresDto, times(1)).convert(playerScores);
        verify(commentToCommentDto, times(1)).convert(comments);
    }
}
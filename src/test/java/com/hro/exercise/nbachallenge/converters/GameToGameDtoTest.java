package com.hro.exercise.nbachallenge.converters;

import com.hro.exercise.nbachallenge.command.CommentDto;
import com.hro.exercise.nbachallenge.command.GameDto;
import com.hro.exercise.nbachallenge.command.PlayerScoresDto;
import com.hro.exercise.nbachallenge.persistence.dao.GameRepository;
import com.hro.exercise.nbachallenge.persistence.model.Comment;
import com.hro.exercise.nbachallenge.persistence.model.Game;
import com.hro.exercise.nbachallenge.persistence.model.PlayerScores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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

        assertEquals(game.getGameDate(), gameDto.getGameDate());
        assertEquals(game.getCommentList(), comments);
        assertEquals(game.getPlayerScores(), playerScores);
        assertEquals(game.getGameId(), gameId);
        assertEquals(game.getHomeTeamName(), homeTeamName);
        assertEquals(game.getVisitorTeamName(), visitorTeamName);
        assertEquals(game.getHomeTeamScore(), score);
        assertEquals(game.getVisitorTeamScore(), score);

        verify(playerScoresToPlayerScoresDto, times(1)).convert(playerScores);
        verify(commentToCommentDto, times(1)).convert(comments);
    }
}
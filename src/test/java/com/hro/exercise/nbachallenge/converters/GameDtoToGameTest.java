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

class GameDtoToGameTest {

    @MockBean
    private GameRepository gameRepository = mock(GameRepository.class);
    @MockBean
    @Autowired
    private PlayerScoresDtoToPlayerScores playerScoresDtoToPlayerScores = mock(PlayerScoresDtoToPlayerScores.class);
    @MockBean
    private CommentDtoToComment commentDtoToComment = mock(CommentDtoToComment.class);
    @MockBean
    private AbstractConverter abstractConverter = mock(AbstractConverter.class);


    @Test
    void convert() {
        GameDtoToGame gameDtoToGame = new GameDtoToGame();
        gameDtoToGame.setPlayerScoresDtoToPlayerScores(playerScoresDtoToPlayerScores);
        gameDtoToGame.setGameRepository(gameRepository);
        gameDtoToGame.setCommentDtoToComment(commentDtoToComment);

        GameDto gameDto = new GameDto();
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


        gameDto.setPlayerScores(playerScoresDtoList);
        gameDto.setGameDate(date);
        gameDto.setVisitorTeamScore(score);
        gameDto.setHomeTeamScore(score);
        gameDto.setHomeTeamName(homeTeamName);
        gameDto.setVisitorTeamName(visitorTeamName);
        gameDto.setComments(commentDtoList);
        gameDto.setGameId(100);

        when(playerScoresDtoToPlayerScores.convert(anyList())).thenReturn(playerScores);
        when(commentDtoToComment.convert(anyList())).thenReturn(comments);
        when(gameRepository.getOne(anyInt())).thenReturn(new Game());

        Game game = gameDtoToGame.convert(gameDto);

        assertTrue(game.getGameDate().equals(gameDto.getGameDate()));
        assertTrue(game.getCommentList().equals(comments));
        assertTrue(game.getPlayerScores().equals(playerScores));
        assertTrue(game.getGameId().equals(gameId));
        assertTrue(game.getHomeTeamName().equals(homeTeamName));
        assertTrue(game.getVisitorTeamName().equals(visitorTeamName));
        assertTrue(game.getHomeTeamScore().equals(score));
        assertTrue(game.getVisitorTeamScore().equals(score));

        verify(playerScoresDtoToPlayerScores, times(1)).convert(playerScoresDtoList);
    }
}
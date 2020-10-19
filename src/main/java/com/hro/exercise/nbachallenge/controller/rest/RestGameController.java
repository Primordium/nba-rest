package com.hro.exercise.nbachallenge.controller.rest;

import com.hro.exercise.nbachallenge.command.GameDto;
import com.hro.exercise.nbachallenge.converters.GameDtoToGame;
import com.hro.exercise.nbachallenge.converters.GameToGameDto;
import com.hro.exercise.nbachallenge.exception.NbaChallengeException;
import com.hro.exercise.nbachallenge.persistence.dao.CommentRepository;
import com.hro.exercise.nbachallenge.persistence.dao.GameRepository;
import com.hro.exercise.nbachallenge.persistence.dao.PlayerScoresRepository;
import com.hro.exercise.nbachallenge.persistence.model.Game;
import com.hro.exercise.nbachallenge.util.RapidApiConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

// List Games for a Given Date (needs Date)
// Returns data for a single game (needs gameId)

@RestController
@RequestMapping("/api/nbadb")
public class RestGameController {


    private PlayerScoresRepository playerScoresRepository;
    private GameRepository gameRepository;
    private CommentRepository commentRepository;
    private GameToGameDto gameToGameDto;

    private RapidApiConnection rapidApiConnection = new RapidApiConnection();
    private GameDtoToGame gameDtoToGame;

    @Autowired
    public void setGameToGameDto(GameToGameDto gameToGameDto) {
        this.gameToGameDto = gameToGameDto;
    }

    @Autowired
    public void setGameDtoToGame(GameDtoToGame gameDtoToGame) {
        this.gameDtoToGame = gameDtoToGame;
    }

    @Autowired
    public void setRapidApiConnection(RapidApiConnection rapidApiConnection) {
        this.rapidApiConnection = rapidApiConnection;
    }

    @Autowired
    public void setPlayerScoresRepository(PlayerScoresRepository playerScoresRepository) {
        this.playerScoresRepository = playerScoresRepository;
    }

    @Autowired
    public void setGameRepository(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Autowired
    public void setCommentRepository(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @GetMapping(path = {"/", ""})
    public String usageInstructions() {
        return "Instructions";
    }


    @GetMapping("/date")
    public List<GameDto> getGamesByDate(
            @RequestParam(value = "date", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) throws NbaChallengeException {
        return getGamesByDateWithPath(date);
    }

    @GetMapping("date/{date}")
    public List<GameDto> getGamesByDateWithPath(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) throws NbaChallengeException {
        List<Game> dbList = gameRepository.findByGameDate(date);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<GameDto> apiList = rapidApiConnection.getGamesByDate(dateFormat.format(date));
        if(apiList.size() > dbList.size()) {
            gameRepository.saveAll(gameDtoToGame.convert(apiList));
            gameRepository.flush();
        }
        return gameToGameDto.convert(gameRepository.findByGameDate(date));

    }

    @GetMapping("/game/{gameId}")
    public GameDto getGameByIdWithPath(@PathVariable Integer gameId) {
        if (gameRepository.findByGameId(gameId) != null) {
            return gameToGameDto.convert(gameRepository.findByGameId(gameId));
        } else {
            GameDto gameDto = rapidApiConnection.getGameById(gameId);
            gameRepository.save(gameDtoToGame.convert(gameDto));
            return gameDto;
        }
    }

    @GetMapping("game")
    public GameDto getGameById(
            @RequestParam(value = "gameid", required = true) Integer gameId) {
        return getGameByIdWithPath(gameId);
    }
}

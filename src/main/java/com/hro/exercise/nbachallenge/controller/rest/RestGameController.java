package com.hro.exercise.nbachallenge.controller.rest;

import com.hro.exercise.nbachallenge.command.GameDto;
import com.hro.exercise.nbachallenge.converters.GameDtoToGame;
import com.hro.exercise.nbachallenge.persistence.model.Game;
import com.hro.exercise.nbachallenge.service.CommentService;
import com.hro.exercise.nbachallenge.service.GameService;
import com.hro.exercise.nbachallenge.service.PlayerScoresService;
import com.hro.exercise.nbachallenge.util.RapidApiConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

// List Games for a Given Date (needs Date)
// Returns data for a single game (needs gameId)

@RestController
@RequestMapping("/api/nbadb")
public class RestGameController {

    private RapidApiConnection rapidApiConnection = new RapidApiConnection();
    private GameService gameService;
    private CommentService commentService;
    private PlayerScoresService playerScoresService;
    private GameDtoToGame gameDtoToGame;

    @Autowired
    public void setGameDtoToGame(GameDtoToGame gameDtoToGame) {
        this.gameDtoToGame = gameDtoToGame;
    }

    @Autowired
    public void setRapidApiConnection(RapidApiConnection rapidApiConnection) {
        this.rapidApiConnection = rapidApiConnection;
    }

    @Autowired
    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }
    @Autowired
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }
    @Autowired
    public void setPlayerScoresService(PlayerScoresService playerScoresService) {
        this.playerScoresService = playerScoresService;
    }

    @GetMapping(path = {"/", ""})
    public String usageInstructions() {
        return "Instructions";
    }


    @GetMapping("/date")
    public List<Game> getGamesByDate(
            @RequestParam(value = "date", required = true) Date dateOrNull) {
        return null;
    }

    @GetMapping("date/{date}")
    public List<Game> getGamesByDateWithPath(@PathVariable Date date) {
        return null;
    }

    @GetMapping("/game/{gameId}")
    public Game getGameByIdWithPath(@PathVariable Integer gameId) {
        if(gameService.getByGameId(gameId) != null) {
            System.out.println("found");
            return gameService.getByGameId(gameId);
        } else {
            System.out.println("searching api");
            GameDto gameDto = rapidApiConnection.getGameById(gameId);
            gameService.save(gameDtoToGame.convert(gameDto));
        }
        return null;
    }

    @GetMapping("game")
    public Game getGameById(
            @RequestParam(value = "gameid", required = true) String gameId){
        return null;
    }
}

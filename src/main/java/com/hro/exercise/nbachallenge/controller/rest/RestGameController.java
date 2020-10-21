package com.hro.exercise.nbachallenge.controller.rest;

import com.hro.exercise.nbachallenge.command.GameDto;
import com.hro.exercise.nbachallenge.converters.GameDtoToGame;
import com.hro.exercise.nbachallenge.converters.GameToGameDto;
import com.hro.exercise.nbachallenge.persistence.dao.GameRepository;
import com.hro.exercise.nbachallenge.persistence.model.Game;
import com.hro.exercise.nbachallenge.util.RapidApiConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/api/nbadb")
public class RestGameController {

    private GameRepository gameRepository;
    private GameToGameDto gameToGameDto;
    private RapidApiConnection rapidApiConnection = new RapidApiConnection();
    private GameDtoToGame gameDtoToGame;
    private static final Logger log = LoggerFactory.getLogger(RestCommentController.class);

    /**
     * Sets the game to gameDto converter
     *
     * @param gameToGameDto
     */
    @Autowired
    public void setGameToGameDto(GameToGameDto gameToGameDto) {
        this.gameToGameDto = gameToGameDto;
    }

    /**
     * Sets the gameDtoToGame converter
     *
     * @param gameDtoToGame
     */
    @Autowired
    public void setGameDtoToGame(GameDtoToGame gameDtoToGame) {
        this.gameDtoToGame = gameDtoToGame;
    }

    /**
     * Sets rapidApiConnection
     *
     * @param rapidApiConnection
     */
    @Autowired
    public void setRapidApiConnection(RapidApiConnection rapidApiConnection) {
        this.rapidApiConnection = rapidApiConnection;
    }

    /**
     * Sets game repository
     *
     * @param gameRepository
     */
    @Autowired
    public void setGameRepository(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    /**
     * Provides instructions for the use of the rest service
     *
     * @return
     */
    @GetMapping(path = {"/", ""})
    public ResponseEntity<?> usageInstructions() {
        return new ResponseEntity<>("Usage: \n" +
                "@pathparamenters should be /api/nbadb/ \n" +
                "GET date/{date} with yyyy-MM-dd format \n" +
                "GET game/{gameId} where id is the game you searching for \n" +
                "POST comments/{gameId} and in the body the text you want to post \n" +
                "PUT comments/edit/{commentId} to edit the comment\n" +
                "DELETE comments/{commentId} to delete a specific comment \n" +
                "", HttpStatus.OK);
    }

    /**
     * @see RestGameController#getGameByIdWithPath(Integer)
     */
    @GetMapping("/date")
    public ResponseEntity<?> getGamesByDate(
            @RequestParam(value = "date", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return getGamesByDateWithPath(date);
    }

    /**
     * @see RestGameController#getGameByIdWithPath(Integer)
     */
    @GetMapping("game")
    public ResponseEntity<?> getGameById(
            @RequestParam(value = "gameid", required = true) Integer gameId) {
        return getGameByIdWithPath(gameId);
    }

    /**
     * Searches games with the provided date, and compares to a result list of the API,
     * Adds new games if needed;
     *
     * @param date
     * @return ResponseEntity<List < GameDto>>
     */
    @GetMapping("date/{date}")
    public ResponseEntity<?> getGamesByDateWithPath(@PathVariable @Validated @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {

        log.info("Game : Searching games with date " + date + " in Database");
        List<Game> dbList = gameRepository.findByGameDate(date);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        log.info("Game : Searching games with date " + date + " in API");
        List<GameDto> apiList = rapidApiConnection.getGamesByDate(dateFormat.format(date));

        if (apiList.size() > dbList.size()) {
            log.info("Game : Saving games. Api provided more games to the given date");
            gameRepository.saveAll(gameDtoToGame.convert(apiList));
            gameRepository.flush();
        }

        if (apiList.isEmpty() && dbList.isEmpty()) {
            log.info("Game: There where no games to the given date: " + date);
            return new ResponseEntity("There where no games for the give date",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(gameToGameDto.convert(gameRepository.findByGameDate(date)), HttpStatus.OK);
    }

    /**
     * Searches for the game with the provided Id, first internally after with the API;
     *
     * @param gameId the gameId of the game to search for
     * @return ResponseEntity<GameDto>
     */
    @GetMapping("/game/{gameId}")
    public ResponseEntity<?> getGameByIdWithPath(@PathVariable Integer gameId) {

        if (gameRepository.findByGameId(gameId) != null) {
            log.info("GAME : The game with ID: " + gameId + " was found in Database");
            GameDto gameDto = gameToGameDto.convert(gameRepository.findByGameId(gameId));
            Collections.reverse(gameDto.getComments());
            return new ResponseEntity<>(gameDto, HttpStatus.OK);
        }

        log.info("GAME : Searching the game with ID: " + gameId + " in API");
        GameDto gameDto = rapidApiConnection.getGameById(gameId);
        if (gameDto.getGameId() == null) {
            return new ResponseEntity("Couldn't find any game with provided ID", HttpStatus.NOT_FOUND);
        }
        gameRepository.save(gameDtoToGame.convert(gameDto));
        Collections.sort(gameDto.getComments(), Collections.reverseOrder());
        return new ResponseEntity<>(gameDto, HttpStatus.OK);

    }
}


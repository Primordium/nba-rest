package com.hro.exercise.nbachallenge.controller.rest;

import com.hro.exercise.nbachallenge.command.GameDto;
import com.hro.exercise.nbachallenge.converters.GameDtoToGame;
import com.hro.exercise.nbachallenge.converters.GameToGameDto;
import com.hro.exercise.nbachallenge.util.Messages;
import com.hro.exercise.nbachallenge.exception.rest.CustomException;
import com.hro.exercise.nbachallenge.exception.rest.ResourceNotFound;
import com.hro.exercise.nbachallenge.persistence.dao.GameRepository;
import com.hro.exercise.nbachallenge.persistence.model.Game;
import com.hro.exercise.nbachallenge.util.AppConstants;
import com.hro.exercise.nbachallenge.util.RapidApiConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
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
    private static final Logger LOG = LoggerFactory.getLogger(RestGameController.class);

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
                "@pathparameters should be /api/nbadb/ \n" +
                "GET date/{date} with yyyy-MM-dd format \n" +
                "GET game/{gameId} where id is the game you searching for \n" +
                "POST comments/{gameId} and in the body the text you want to post \n" +
                "PUT comments/{commentId} to edit the comment\n" +
                "DELETE comments/{commentId} to delete a specific comment \n" +
                "", HttpStatus.OK);
    }

    /**
     * @see RestGameController#getGameByIdWithPath(Integer)
     */
    @GetMapping("/date")
    public ResponseEntity<?> getGamesByDate(
            @RequestParam(value = "date", required = true)
            @Validated @DateTimeFormat(pattern = AppConstants.DAY_TIME_FORMAT) Date date) throws ResourceNotFound, CustomException {
        return getGamesByDateWithPath(date);
    }

    /**
     * @see RestGameController#getGameByIdWithPath(Integer)
     */
    @GetMapping("game")
    public ResponseEntity<?> getGameById(@RequestParam(value = "gameid", required = true)
                                             @Validated Integer gameId) throws ResourceNotFound {
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
    public ResponseEntity<?> getGamesByDateWithPath(@PathVariable @Validated @DateTimeFormat
            (pattern = AppConstants.DAY_TIME_FORMAT) Date date) throws ResourceNotFound, CustomException {

        if(date.after(Date.from(Instant.now()))){
            throw new CustomException("Please provide a Date in the past");
        }

        List<Game> dbList;
        List<GameDto> apiList;
        DateFormat dateFormat = new SimpleDateFormat(AppConstants.DAY_TIME_FORMAT);

        LOG.info("Searching Database games with date '" + date + "'");
        dbList = gameRepository.findByGameDate(date);
        apiList = rapidApiConnection.getGamesByDate(dateFormat.format(date));

        if (apiList.size() > dbList.size()) {
            LOG.info("Merging, Rapid Api provided more games to the given date");
            gameRepository.saveAll(gameDtoToGame.convert(apiList));
            gameRepository.flush();
        }

        if (apiList.isEmpty() && dbList.isEmpty()) {
            LOG.info(Messages.GAME_NOT_FOUND_WITH_DATE + date);
            throw new ResourceNotFound(Messages.GAME_NOT_FOUND_WITH_DATE);
        }

        return ResponseEntity.ok(gameToGameDto.convert(gameRepository.findByGameDate(date)));
    }

    /**
     * Searches for the game with the provided Id, first internally after with the API;
     *
     * @param gameId the gameId of the game to search for
     * @return ResponseEntity<GameDto>
     */
    @GetMapping("/game/{gameId}")
    public ResponseEntity<?> getGameByIdWithPath(@PathVariable @Validated Integer gameId) throws ResourceNotFound {

        GameDto gameDto;

        if (gameRepository.findByGameId(gameId) != null) {
            LOG.info("Game found in DB the game with ID: '" + gameId + "'");
            gameDto = gameToGameDto.convert(gameRepository.findByGameId(gameId));
            Collections.reverse(gameDto.getComments());

            return ResponseEntity.ok(gameDto);
        }

        LOG.info(Messages.GAME_NOT_FOUND_WITH_ID + gameId);
        gameDto = rapidApiConnection.getGameById(gameId);

        if (gameDto == null) {
            LOG.info(Messages.GAME_NOT_FOUND_WITH_ID + gameId);
            throw new ResourceNotFound(Messages.COMMENT_NOT_FOUND_WITH_ID + gameId);
        }

        gameRepository.save(gameDtoToGame.convert(gameDto));
        LOG.info(Messages.GAME_SAVE + gameId);
        gameDto.getComments().sort(Collections.reverseOrder());

        return ResponseEntity.ok(gameDto);
    }

}


package com.hro.exercise.nbachallenge.controller;

import com.hro.exercise.nbachallenge.persistence.model.Game;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

// List Games for a Given Date (needs Date)
// Returns data for a single game (needs gameId)

@RestController
@RequestMapping("/api/nbadb")
public class GameController {

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

    @GetMapping("/game/{id}")
    public Game getGameByIdWithPath(@PathVariable Integer gameId) {
        return null;
    }

    @GetMapping("game")
    public Game getGameById(
            @RequestParam(value = "gameid", required = true) String gameId){
        return null;
    }
}

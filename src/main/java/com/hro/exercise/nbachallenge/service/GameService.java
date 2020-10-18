package com.hro.exercise.nbachallenge.service;

import com.hro.exercise.nbachallenge.persistence.model.Game;

import java.util.Date;
import java.util.List;


public interface GameService {
    Game get(Integer id);
    Game save(Game game);
    void delete(Integer id);
    Game getByGameId(Integer id);
    List<Game> getByGameDate(Date date);
}

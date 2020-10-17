package com.hro.exercise.nbachallenge.service;

import com.hro.exercise.nbachallenge.persistence.model.Game;


public interface GameService {
    Game get(Integer id);
    Game save(Game game);
    void delete(Integer id);
    Game getByGameId(Integer id);
}

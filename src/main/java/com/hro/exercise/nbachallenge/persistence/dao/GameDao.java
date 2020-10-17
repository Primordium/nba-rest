package com.hro.exercise.nbachallenge.persistence.dao;

import com.hro.exercise.nbachallenge.persistence.model.Game;

public interface GameDao extends Dao<Game>{

    Game findByGameId(Integer gameId);
}

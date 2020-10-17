package com.hro.exercise.nbachallenge.service;

import com.hro.exercise.nbachallenge.persistence.model.Player;

public interface PlayerService {
    Player get(Integer id);
    Player save(Player player);
    void delete(Integer id);
}

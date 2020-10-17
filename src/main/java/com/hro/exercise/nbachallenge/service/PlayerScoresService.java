package com.hro.exercise.nbachallenge.service;

import com.hro.exercise.nbachallenge.persistence.model.PlayerScores;

public interface PlayerScoresService {
    PlayerScores get(Integer id);
    PlayerScores save(PlayerScores playerScores);
    void delete(Integer id);
}

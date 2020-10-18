package com.hro.exercise.nbachallenge.persistence.dao;

import com.hro.exercise.nbachallenge.persistence.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

    Game findByGameId(Integer gameId);
}

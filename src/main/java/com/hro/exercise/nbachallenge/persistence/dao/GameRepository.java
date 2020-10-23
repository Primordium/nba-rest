package com.hro.exercise.nbachallenge.persistence.dao;

import com.hro.exercise.nbachallenge.persistence.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
/**
 * Using JpaRepository to have access to the methods provided when working with persistence
 * examples : findAll , get(id);
 */
@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

    /**
     * Finds and retrieves Game Object based on the property gameId;
     * @param gameId the value to search for
     * @return Game with the provided game id;
     */
    Game findByGameId(Integer gameId);

    /**
     * Finds and retrieves Game by given date
     * @param gameDate the date to search for;
     * @return List of Games with date;
     */
    List<Game> findByGameDate(Date gameDate);
}

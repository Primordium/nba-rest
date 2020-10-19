package com.hro.exercise.nbachallenge.persistence.dao;

import com.hro.exercise.nbachallenge.persistence.model.PlayerScores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * Using JpaRepository to have access to the methods provided when working with persistence
 * examples : findAll , get(id);
 */
@Repository
public interface PlayerScoresRepository extends JpaRepository<PlayerScores, Integer> {
}

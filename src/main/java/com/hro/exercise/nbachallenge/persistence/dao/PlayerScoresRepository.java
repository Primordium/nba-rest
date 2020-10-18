package com.hro.exercise.nbachallenge.persistence.dao;

import com.hro.exercise.nbachallenge.persistence.model.PlayerScores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerScoresRepository extends JpaRepository<PlayerScores, Integer> {
}

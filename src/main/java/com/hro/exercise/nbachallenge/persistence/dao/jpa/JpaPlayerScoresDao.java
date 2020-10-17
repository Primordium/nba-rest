package com.hro.exercise.nbachallenge.persistence.dao.jpa;

import com.hro.exercise.nbachallenge.persistence.dao.PlayerScoresDao;
import com.hro.exercise.nbachallenge.persistence.model.PlayerScores;
import org.springframework.stereotype.Repository;

@Repository
public class JpaPlayerScoresDao extends GenericJpaDao<PlayerScores> implements PlayerScoresDao {
    /**
     * Initializes a new JPA DAO instance given a model type
     *
     *
     */
    public JpaPlayerScoresDao() {
        super(PlayerScores.class);
    }
}

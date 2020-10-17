package com.hro.exercise.nbachallenge.persistence.dao.jpa;

import com.hro.exercise.nbachallenge.persistence.dao.PlayerDao;
import com.hro.exercise.nbachallenge.persistence.model.Player;
import org.springframework.stereotype.Repository;

@Repository
public class JpaPlayerDao extends GenericJpaDao<Player> implements PlayerDao {
    /**
     * Initializes a new JPA DAO instance given a model type
     *
     *
     */
    public JpaPlayerDao() {
        super(Player.class);
    }
}

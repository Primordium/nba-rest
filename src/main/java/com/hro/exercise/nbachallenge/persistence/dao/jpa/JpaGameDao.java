package com.hro.exercise.nbachallenge.persistence.dao.jpa;

import com.hro.exercise.nbachallenge.persistence.dao.GameDao;
import com.hro.exercise.nbachallenge.persistence.model.Game;
import org.springframework.stereotype.Repository;

@Repository
public class JpaGameDao extends GenericJpaDao<Game> implements GameDao {

    public JpaGameDao() {
        super(Game.class);
    }
}

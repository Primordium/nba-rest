package com.hro.exercise.nbachallenge.persistence.dao.jpa;

import com.hro.exercise.nbachallenge.persistence.dao.GameDao;
import com.hro.exercise.nbachallenge.persistence.model.Game;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JpaGameDao extends GenericJpaDao<Game> implements GameDao {

    public JpaGameDao() {
        super(Game.class);
    }


    public Game findByGameId(Integer gameIdToSearch) {
           List result = em.createNativeQuery("SELECT * FROM GAMES WHERE GAME_ID = :value", Game.class).setParameter("value", gameIdToSearch).getResultList();
           if(result.isEmpty()) {
               return null;
           }
           return (Game) result.get(0);
    }

}

package com.hro.exercise.nbachallenge.service;

import com.hro.exercise.nbachallenge.persistence.dao.jpa.JpaGameDao;
import com.hro.exercise.nbachallenge.persistence.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class GameServiceImpl implements GameService{

    private JpaGameDao gameDao;

    @Autowired
    public void setJpaGameDao(JpaGameDao jpaGameDao) {
        this.gameDao = jpaGameDao;
    }

    @Override
    public Game get(Integer id) {
        return gameDao.findById(id);
    }

    @Transactional
    @Override
    public Game save(Game game) {
        game.getPlayerScores().stream().forEach(e -> {
            e.setGame(game);
        });
        game.getCommentList().stream().forEach(e -> {
            e.setGame(game);
        });
        return gameDao.saveOrUpdate(game);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        gameDao.delete(id);
    }

    @Override
    public Game getByGameId(Integer id) {
       return gameDao.findByGameId(id);
    }

    @Override
    public List<Game> getByGameDate(Date date) {
        System.out.println(gameDao.findByGameDate(date));
        return gameDao.findByGameDate(date);
    }

}

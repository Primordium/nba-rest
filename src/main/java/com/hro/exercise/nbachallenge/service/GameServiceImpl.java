package com.hro.exercise.nbachallenge.service;

import com.hro.exercise.nbachallenge.persistence.dao.jpa.JpaGameDao;
import com.hro.exercise.nbachallenge.persistence.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class GameServiceImpl implements GameService{

    private JpaGameDao gameDao;

    @Autowired
    public JpaGameDao getJpaGameDao() {
        return gameDao;
    }
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
        return  gameDao.saveOrUpdate(game);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        gameDao.delete(id);
    }
}

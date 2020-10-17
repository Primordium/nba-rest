package com.hro.exercise.nbachallenge.service;

import com.hro.exercise.nbachallenge.persistence.dao.jpa.JpaPlayerDao;
import com.hro.exercise.nbachallenge.persistence.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerServiceImpl implements PlayerService{

    private JpaPlayerDao playerDao;

    @Autowired
    public JpaPlayerDao getPlayerDao() {
        return playerDao;
    }
    @Autowired
    public void setPlayerDao(JpaPlayerDao playerDao) {
        this.playerDao = playerDao;
    }

    @Override
    public Player get(Integer id) {
        return playerDao.findById(id);
    }
}

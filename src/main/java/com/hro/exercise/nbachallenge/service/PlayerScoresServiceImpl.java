package com.hro.exercise.nbachallenge.service;

import com.hro.exercise.nbachallenge.persistence.dao.jpa.JpaPlayerScoresDao;
import com.hro.exercise.nbachallenge.persistence.model.PlayerScores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PlayerScoresServiceImpl implements PlayerScoresService {

    private JpaPlayerScoresDao playerDao;

    @Autowired
    public JpaPlayerScoresDao getPlayerDao() {
        return playerDao;
    }
    @Autowired
    public void setPlayerDao(JpaPlayerScoresDao playerDao) {
        this.playerDao = playerDao;
    }

    @Override
    public PlayerScores get(Integer id) {
        return playerDao.findById(id);
    }

    @Transactional
    @Override
    public PlayerScores save(PlayerScores playerScores) {
        return playerDao.saveOrUpdate(playerScores);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        playerDao.delete(id);
    }
}

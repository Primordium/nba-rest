package com.hro.exercise.nbachallenge.service;

import com.hro.exercise.nbachallenge.persistence.dao.jpa.JpaCommentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private JpaCommentDao commentDao;

    @Autowired
    public JpaCommentDao getCommentDao() {
        return commentDao;
    }

    @Autowired
    public void setCommentDao(JpaCommentDao commentDao) {
        this.commentDao = commentDao;
    }
}

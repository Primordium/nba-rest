package com.hro.exercise.nbachallenge.service;

import com.hro.exercise.nbachallenge.persistence.dao.jpa.CommentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private CommentDao commentDao;

    @Autowired
    public CommentDao getCommentDao() {
        return commentDao;
    }

    @Autowired
    public void setCommentDao(CommentDao commentDao) {
        this.commentDao = commentDao;
    }
}

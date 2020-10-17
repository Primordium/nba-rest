package com.hro.exercise.nbachallenge.service;

import com.hro.exercise.nbachallenge.persistence.dao.jpa.JpaCommentDao;
import com.hro.exercise.nbachallenge.persistence.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CommentServiceImpl implements CommentService{

    private JpaCommentDao commentDao;

    @Autowired
    public JpaCommentDao getCommentDao() {
        return commentDao;
    }

    @Autowired
    public void setCommentDao(JpaCommentDao commentDao) {
        this.commentDao = commentDao;
    }

    @Override
    public Comment get(Integer id) {
        return commentDao.findById(id);
    }

    @Override
    public String getComment(Integer id) {
        return commentDao.findById(id).getComment();
    }

    @Transactional
    @Override
    public Comment save(Comment comment) {
        return commentDao.saveOrUpdate(comment);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        commentDao.delete(id);
    }
}

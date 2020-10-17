package com.hro.exercise.nbachallenge.service;

import com.hro.exercise.nbachallenge.persistence.model.Comment;

public interface CommentService {

    Comment get(Integer id);
    String getComment(Integer id);
    Comment save(Comment comment);
    void delete(Integer id);

}

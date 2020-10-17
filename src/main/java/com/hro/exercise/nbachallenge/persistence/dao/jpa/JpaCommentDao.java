package com.hro.exercise.nbachallenge.persistence.dao.jpa;

import com.hro.exercise.nbachallenge.persistence.dao.CommentDao;
import com.hro.exercise.nbachallenge.persistence.model.Comment;
import org.springframework.stereotype.Repository;

@Repository
public class JpaCommentDao extends GenericJpaDao<Comment> implements CommentDao {

    public JpaCommentDao() {
        super(Comment.class);
    }

}

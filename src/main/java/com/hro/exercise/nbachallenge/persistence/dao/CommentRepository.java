package com.hro.exercise.nbachallenge.persistence.dao;

import com.hro.exercise.nbachallenge.persistence.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
}

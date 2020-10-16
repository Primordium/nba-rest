package com.hro.exercise.nbachallenge.controller;

import com.hro.exercise.nbachallenge.persistence.model.Comment;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nbadb")
public class CommentController {

    // Creates new Comment (requires gameId)
    // Updates a Comment (requires commentId)
    // Deletes a Comment (requires commentId)

    @GetMapping("comments/{gameId}")
    public List<Comment> getCommentsForGameId(@PathVariable Integer gameId){
        return null;
    }

    @PutMapping("comments/{id}")
    public List<Comment> updateCommentById(@PathVariable Integer commentId){
        return null;
    }

    @DeleteMapping("comments/{id}")
    public List<Comment> deleteCommentById(@PathVariable Integer commentId){
        return null;
    }}

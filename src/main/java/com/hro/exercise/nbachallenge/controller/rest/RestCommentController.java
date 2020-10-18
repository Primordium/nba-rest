package com.hro.exercise.nbachallenge.controller.rest;

import com.hro.exercise.nbachallenge.persistence.dao.CommentRepository;
import com.hro.exercise.nbachallenge.persistence.dao.GameRepository;
import com.hro.exercise.nbachallenge.persistence.model.Comment;
import com.hro.exercise.nbachallenge.persistence.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/nbadb")
public class RestCommentController {

    // Creates new Comment (requires gameId)
    // Updates a Comment (requires commentId)
    // Deletes a Comment (requires commentId)
    GameRepository gameRepository;
    CommentRepository commentRepository;


    @Autowired
    public void setGameRepository(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Autowired
    public void setCommentRepository(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @PostMapping("comments/{gameId}")
    public List<Comment> getCommentsForGameId(@PathVariable Integer gameId, @RequestBody String comment) {
        Game game = gameRepository.findByGameId(gameId);
        Comment cmnt = new Comment(comment);
        cmnt.setGame(game);
        cmnt.setDate(cmnt.getUpdateTime());
        game.getCommentList().add(cmnt);
        gameRepository.save(game);
        return null;
    }

    @PutMapping("comments/edit/{commentId}")
    public List<Comment> updateCommentById(@PathVariable Integer commentId, @RequestBody String commentNew) {
        Game game = gameRepository.findByGameId(commentRepository.getOne(commentId).getGame().getGameId());

        List<Comment> commentsList = game.getCommentList();

        for (int i = 0; i < commentsList.size(); i++) {
            if (commentsList.get(i).getId() == commentId) {
                commentsList.get(i).setComment(commentNew);
                commentRepository.save(commentsList.get(i));
                break;
            }
        }

        return null;
    }


    @DeleteMapping("comments/{commentId}")
    public List<Comment> deleteCommentById(@PathVariable Integer commentId) {

        Game game = gameRepository.findByGameId(commentRepository.getOne(commentId).getGame().getGameId());
        List<Comment> commentsList = game.getCommentList();

        for (int i = 0; i < commentsList.size(); i++) {
            if (commentsList.get(i).getId() == commentId) {
                commentsList.remove(i);
                commentRepository.save(commentsList.get(i));
                break;
            }
        }

        return null;
    }
}

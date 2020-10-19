package com.hro.exercise.nbachallenge.controller.rest;

import com.hro.exercise.nbachallenge.persistence.dao.CommentRepository;
import com.hro.exercise.nbachallenge.persistence.dao.GameRepository;
import com.hro.exercise.nbachallenge.persistence.model.Comment;
import com.hro.exercise.nbachallenge.persistence.model.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/nbadb")
public class RestCommentController {

    private GameRepository gameRepository;
    private CommentRepository commentRepository;

    private static final Logger log = LoggerFactory.getLogger(RestCommentController.class);

    /**
     * Sets the game repository
     * @param gameRepository to be set
     */
    @Autowired
    public void setGameRepository(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    /**
     * Sets the comment repository
     * @param commentRepository to be set
     */
    @Autowired
    public void setCommentRepository(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    /**
     * Adds a comment to the commentList of the game with the gameId
     * @param gameId of the game;
     * @param comment to be added to the game comment list
     * @return Response Entity
     */
    @PostMapping("comments/{gameId}")
    public ResponseEntity<?> postCommentsForGameId(@PathVariable Integer gameId, @RequestBody String comment) {
        Game game = gameRepository.findByGameId(gameId);
        if (game == null) {
            log.warn("WARNING : Comment(" + comment + ") could not be added to please check game ID");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Comment cmnt = new Comment(comment);
        cmnt.setGame(game);
        cmnt.setDate(cmnt.getUpdateTime());
        game.getCommentList().add(cmnt);
        Collections.sort(game.getCommentList(), Collections.reverseOrder());
        gameRepository.save(game);
        log.info("COMMENT :" + comment + " added to Game with ID: " + game.getGameId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Possible solution to improve performance, is requiring before hand gameId aswell
     * Edits the comment with the ID provided and replaces it with a new one
     * @param commentId  the Id of the comment to be edited
     * @param commentNew the new comment to replace the new one
     * @return Response Entity
     */

    @PutMapping("comments/edit/{commentId}")
    public ResponseEntity<?> updateCommentById(@PathVariable Integer commentId, @RequestBody String commentNew) {
        commentRepository.findById(commentId).get().getGame();
        Game game = gameRepository.findByGameId(commentRepository.getOne(commentId).getGame().getGameId());
        if (game == null) {
            log.warn("WARNING : The Comment with the ID: " + commentId + " could not be edited, invalid comment id");
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        List<Comment> commentsList = game.getCommentList();

        for (int i = 0; i < commentsList.size(); i++) {
            if (commentsList.get(i).getId() == commentId) {
                commentsList.get(i).setComment(commentNew);
                commentRepository.save(commentsList.get(i));
                Collections.sort(game.getCommentList(), Collections.reverseOrder());
                log.info("COMMENT :" + commentNew + " replaced to the comment with ID: " + commentId);
                break;
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Deletes a comment with the provided Id
     * @param commentId the comment id to be deleted
     * @return Response Entity
     */
    @DeleteMapping("comments/{commentId}")
    public ResponseEntity<?> deleteCommentById(@PathVariable Integer commentId) {

        if (commentRepository.findById(commentId).isEmpty()) {
            log.info("COMMENT : The comment with " + commentId + " was not present and could not be removed");
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        Game game = gameRepository.findByGameId(commentRepository.getOne(commentId).getGame().getGameId());
        List<Comment> commentsList = game.getCommentList();

        for (int i = 0; i < commentsList.size(); i++) {
            if (commentsList.get(i).getId() == commentId) {
                log.info("COMMENT :" + commentsList.get(i).getComment() + " has been deleted from game with ID: " + game.getGameId());
                commentsList.remove(i);
                commentRepository.save(commentsList.get(i));
                break;
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

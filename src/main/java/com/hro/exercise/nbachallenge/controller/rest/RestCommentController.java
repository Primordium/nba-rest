package com.hro.exercise.nbachallenge.controller.rest;

import com.hro.exercise.nbachallenge.command.GameDto;
import com.hro.exercise.nbachallenge.converters.GameDtoToGame;
import com.hro.exercise.nbachallenge.persistence.dao.CommentRepository;
import com.hro.exercise.nbachallenge.persistence.dao.GameRepository;
import com.hro.exercise.nbachallenge.persistence.model.Comment;
import com.hro.exercise.nbachallenge.persistence.model.Game;
import com.hro.exercise.nbachallenge.util.RapidApiConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/nbadb")
public class RestCommentController {

    private GameRepository gameRepository;
    private CommentRepository commentRepository;
    private RapidApiConnection rapidApiConnection;
    private GameDtoToGame gameDtoToGame;

    private static final Logger LOG = LoggerFactory.getLogger(RestCommentController.class);

    /**
     * Sets the game repository
     *
     * @param gameRepository to be set
     */
    @Autowired
    public void setGameRepository(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    /**
     * Sets the comment repository
     *
     * @param commentRepository to be set
     */
    @Autowired
    public void setCommentRepository(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    /**
     * Sets Api connection
     *
     * @param rapidApiConnection
     */
    @Autowired
    public void setRapidApiConnection(RapidApiConnection rapidApiConnection) {
        this.rapidApiConnection = rapidApiConnection;
    }

    /**
     * Sets gameDto to Game converter
     *
     * @param gameDtoToGame
     */
    @Autowired
    public void setGameDtoToGame(GameDtoToGame gameDtoToGame) {
        this.gameDtoToGame = gameDtoToGame;
    }

    /**
     * Adds a comment to the commentList of the game with the gameId
     *
     * @param gameId  of the game;
     * @param comment to be added to the game comment list
     * @return Response Entity
     */
    @PostMapping("comments/{gameId}")
    public ResponseEntity<?> postCommentsForGameId(@PathVariable Integer gameId, @RequestBody String comment) {

        ResponseEntity<?> responseEntity = null;
        Comment cmnt = new Comment(comment);
        Game game;
        GameDto gameDto;

        if (comment.isEmpty()) {
            LOG.warn("COMMENT : Comment discarded, reason : Empty comment");
            responseEntity = new ResponseEntity<>("Could not use your comment. \nReason: Comment is empty", HttpStatus.BAD_REQUEST);
        }

        game = gameRepository.findByGameId(gameId);

        if (game == null && responseEntity == null) {
            gameDto = rapidApiConnection.getGameById(gameId);
            if (gameDto == null) {
                LOG.warn("COMMENT : Game with id '" + gameId + "' not found, could NOT add comment '" + comment + "'");
                responseEntity = new ResponseEntity<>("Could not find game in Database", HttpStatus.NOT_FOUND);
            }

            gameRepository.save(gameDtoToGame.convert(gameDto));
        }
        if(responseEntity == null) {
            game = gameRepository.findByGameId(gameId);
            cmnt.initProperties(game);
            game.getCommentList().add(cmnt);
            commentRepository.save(cmnt);
            gameRepository.save(game);
            LOG.info("COMMENT : Added to game with id: '" + gameId + "' comment '" + comment + "'");
            responseEntity = new ResponseEntity<>("Your comment: '" + comment + "' had been added to the game with ID: " + gameId, HttpStatus.CREATED);
        }
        return responseEntity;
    }

    /**
     * Possible solution to improve performance, is requiring before hand gameId aswell
     * Edits the comment with the ID provided and replaces it with a new one
     *
     * @param commentId  the Id of the comment to be edited
     * @param commentNew the new comment to replace the new one
     * @return Response Entity
     */
    @PutMapping("comments/{commentId}")
    public ResponseEntity<?> updateCommentById(@PathVariable Integer commentId, @RequestBody String commentNew) {

        Comment comment;
        ResponseEntity<?> responseEntity = null;

        if (commentNew.isEmpty()) {
            LOG.warn("COMMENT : Comment discarded, reason : Empty comment");
            responseEntity = new ResponseEntity<>("Could not use your comment. \nReason: Comment is empty",
                    HttpStatus.BAD_REQUEST);
        }

        if (commentRepository.findById(commentId).isEmpty() && responseEntity == null) {
            LOG.warn("COMMENT : Could not find the comment with the ID: '" + commentId + "'");
            responseEntity = new ResponseEntity<>("Could not find a comment with the provided commentID",
                    HttpStatus.NOT_FOUND);
        }

        if (responseEntity == null) {
            comment = commentRepository.getOne(commentId);
            comment.editComment(commentNew);
            LOG.info("COMMENT : The comment with id: '" + commentId + "' has been replaced for '" + commentNew + "'");
            gameRepository.save(comment.getGame());
            responseEntity = new ResponseEntity<>("Comment with id :'" + commentId + "' " +
                    "has been replaced with : '" + commentNew + "'", HttpStatus.OK);
        }

        return responseEntity;
    }


    /**
     * Deletes a comment with the provided Id
     *
     * @param commentId the comment id to be deleted
     * @return Response Entity
     */
    @DeleteMapping("comments/{commentId}")
    public ResponseEntity<?> deleteCommentById(@PathVariable Integer commentId) {
        ResponseEntity<?> responseEntity = null;

        if (commentRepository.findById(commentId).isEmpty()) {
            LOG.info("COMMENT : The comment with '" + commentId + "' could not be removed\nReason : Not present");
            responseEntity = new ResponseEntity<>("Could not find a comment with the provided commentID", HttpStatus.NOT_FOUND);
        }

        if (responseEntity == null) {
            commentRepository.getOne(commentId).getGame().removeComment(commentId);
            gameRepository.save(commentRepository.getOne(commentId).getGame());
            LOG.info("COMMENT : Removed the comment with comment id : '" + commentId + "'");
            responseEntity = new ResponseEntity<>("Comment with id " + commentId + " has been deleted", HttpStatus.OK);
        }

        return responseEntity;
    }
}

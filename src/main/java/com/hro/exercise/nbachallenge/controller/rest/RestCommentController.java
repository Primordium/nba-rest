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

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/nbadb")
public class RestCommentController {

    private GameRepository gameRepository;
    private CommentRepository commentRepository;
    private RestGameController restGameController;
    private RapidApiConnection rapidApiConnection;
    private GameDtoToGame gameDtoToGame;

    private static final Logger log = LoggerFactory.getLogger(RestCommentController.class);

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

    @Autowired
    public void setRapidApiConnection(RapidApiConnection rapidApiConnection) {
        this.rapidApiConnection = rapidApiConnection;
    }

    @Autowired
    public void setGameDtoToGame(GameDtoToGame gameDtoToGame) {
        this.gameDtoToGame = gameDtoToGame;
    }

    /**
     * Sets rest game controller
     *
     * @param restGameController
     */

    @Autowired
    public void setRestGameController(RestGameController restGameController) {
        this.restGameController = restGameController;
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

        // Could search for the game and add it before posting a comment
        // Need to explore that possibility

        if (comment.isEmpty()) {
            return new ResponseEntity<>("Could not use your comment. \nReason: Comment is empty", HttpStatus.BAD_REQUEST);
        }

        Game game = gameRepository.findByGameId(gameId);

        if (game == null) {
            GameDto gameDto = rapidApiConnection.getGameById(gameId);
            if (gameDto == null) {
                log.warn("WARNING : Comment(" + comment + ") could not be added to please check game ID");
                return new ResponseEntity<>("Could not find game in Database", HttpStatus.NOT_FOUND);
            }
            gameRepository.save(gameDtoToGame.convert(gameDto));
        }
        game = gameRepository.findByGameId(gameId);
        Comment cmnt = new Comment(comment);
        cmnt.setGame(game);
        cmnt.setDate(cmnt.getUpdateTime());
        game.getCommentList().add(cmnt);
        Collections.sort(game.getCommentList());
        commentRepository.save(cmnt);
        gameRepository.save(game);
        log.info("COMMENT :'" + comment + "' added to Game with ID: '" + game.getGameId()+"'");
        return new ResponseEntity<>("Your comment: '" + comment + "' had been added to the game with ID: " + gameId, HttpStatus.CREATED);
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
        if (commentNew.isEmpty()) {
            return new ResponseEntity<>("Could not replace the comment : " + commentId + "\nReason: New comment is empty", HttpStatus.BAD_REQUEST);
        }

        if (commentRepository.findById(commentId).isEmpty()) {
            log.warn("WARNING : The Comment with the ID: " + commentId + " could not be edited, invalid comment id");
            return new ResponseEntity("Could not find a comment with the provided commentID", HttpStatus.NOT_FOUND);
        }

        Comment comment = commentRepository.getOne(commentId);
        comment.editComment(commentNew);
        log.info("COMMENT :" + commentNew + " replaced to the comment with ID: '" + commentId +"'");
        gameRepository.save(comment.getGame());

        return new ResponseEntity<>("Your comment :'" + commentNew + "' replaced to the comment with ID: " + commentId, HttpStatus.OK);
    }

    /**
     * Deletes a comment with the provided Id
     *
     * @param commentId the comment id to be deleted
     * @return Response Entity
     */
    @DeleteMapping("comments/{commentId}")
    public ResponseEntity<?> deleteCommentById(@PathVariable Integer commentId) {

        if (commentRepository.findById(commentId).isEmpty()) {
            log.info("COMMENT : The comment with '" + commentId + "' was not present and could not be removed");
            return new ResponseEntity("Could not find a comment with the provided commentID", HttpStatus.NOT_FOUND);
        }

        commentRepository.getOne(commentId).getGame().removeComment(commentId);
        gameRepository.save(commentRepository.getOne(commentId).getGame());
        return new ResponseEntity<>("Comment with id " + commentId + " has been deleted", HttpStatus.OK);

    }
}

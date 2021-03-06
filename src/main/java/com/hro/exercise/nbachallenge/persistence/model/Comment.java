package com.hro.exercise.nbachallenge.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hro.exercise.nbachallenge.util.AppConstants;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * The comment model entity
 */
@Entity
@Table(name = "comments")
public class Comment extends AbstractModel implements Comparable<Comment> {

    private String comment;

    @ManyToOne
    @JsonIgnore
    private Game game;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = AppConstants.DAY_TIME_FORMAT)
    private Date date;

    public Comment() {
    }

    public Comment(String comment) {
        this.comment = comment;
    }

    /**
     * Method used to change comment and reflect in commentList of game as well
     * @param comment
     */
    public void editComment(String comment) {
        game.updateComment(getId(), comment);
    }

    /**
     * Gets the comment
     * @return String with the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the comment with provided String
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Gets the game reference
     */
    public Game getGame() {
        return game;
    }

    /**
     * Sets game which comments is associated to
     * @param game
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * gets the date of comment publication
     * @return
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the date of comment publication
     */
    public void setDate(Date date) {
        this.date = date;
    }

    public void initProperties(Game game) {
        this.game = game;
        date = getUpdateTime();
    }

    @Override
    public String toString() {
        return "Comment{" +
                "comment='" + comment + '\'' +
                '}';
    }

    @Override
    public int compareTo(Comment o) {
        return (this.getDate() == null || o.getDate() == null) ? 0 : getDate().compareTo(o.getDate());
    }
}

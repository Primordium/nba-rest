package com.hro.exercise.nbachallenge.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "comments")
public class Comment extends AbstractModel {

    private String comment;

    @ManyToOne
    @JsonIgnore
    private Game game;

    private Date date;


    /**
     * Gets the comment
     *
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "comment='" + comment + '\'' +
                '}';
    }


}

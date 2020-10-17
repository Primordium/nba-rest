package com.hro.exercise.nbachallenge.persistence.model;

import javax.persistence.*;

@Entity
@Table(name = "comments")
public class Comment extends AbstractModel {

    private String comment;

    @ManyToOne
    private Game game;


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

    @Override
    public String toString() {
        return "Comment{" +
                "comment='" + comment + '\'' +
                '}';
    }


}

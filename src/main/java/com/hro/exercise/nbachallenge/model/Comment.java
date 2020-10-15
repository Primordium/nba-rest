package com.hro.exercise.nbachallenge.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//@Entity
//@Table(name = "comments")
public class Comment extends AbstractModel{

    @ManyToOne
    private Game game;
    private String comment;

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

    @Override
    public String toString() {
        return "Comment{" +
                "comment='" + comment + '\'' +
                '}';
    }
}

package com.hro.exercise.nbachallenge.persistence.model;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "games")
public class Game extends AbstractModel{

    private Date gameDate;
    private String homeTeamName;
    private String awayTeamName;
    private Integer homeTeamScore;
    private Integer awayTeamScore;

    @ElementCollection
    private Map<Player, Integer> playerScores;


    @OneToMany(
            cascade = {CascadeType.ALL},
            orphanRemoval = true,
            mappedBy = "games",
            fetch = FetchType.EAGER
    )
    private List<Comment> commentList = new ArrayList<>();

    public void addComment(Comment comment) {
        commentList.add(comment);
    }

    public void removeComment(Comment comment) {
        commentList.remove(comment);
        comment.setComment(null);
    }

    public void updateComment(Integer commentId, Comment comment) {
        commentList.get(commentId).setComment(comment.getComment());
    }
}

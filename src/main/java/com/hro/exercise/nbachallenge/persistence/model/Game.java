package com.hro.exercise.nbachallenge.persistence.model;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "games")
public class Game extends AbstractModel{

    private String date;
    private String homeTeamName;
    private String visitorTeamName;
    private Integer homeTeamScore;
    private Integer visitorTeamScore;

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

    @Override
    public String toString() {
        return "Game{" +
                "gameDate=" + date +
                ", home_team='" + homeTeamName + '\'' +
                ", awayTeamName='" + visitorTeamName + '\'' +
                ", home_team_score=" + homeTeamScore +
                ", awayTeamScore=" + visitorTeamScore +
                ", playerScores=" + playerScores +
                ", commentList=" + commentList +
                '}';
    }
}

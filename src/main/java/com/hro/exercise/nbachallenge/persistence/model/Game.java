package com.hro.exercise.nbachallenge.persistence.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "games")
public class Game extends AbstractModel {


    private Date gameDate;
    @Column(name = "gameId", nullable = false)
    private Integer gameId;
    private String homeTeamName;
    private String visitorTeamName;
    private Integer homeTeamScore;
    private Integer visitorTeamScore;

    @ElementCollection
    private Map<Player, Integer> playerScores;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
            //mappedBy = "games"
            //fetch = FetchType.EAGER
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

    public Date getGameDate() {
        return gameDate;
    }

    public void setGameDate(Date date) {
        this.gameDate = date;
    }


    public String getHomeTeamName() {
        return homeTeamName;
    }

    public void setHomeTeamName(String homeTeamName) {
        this.homeTeamName = homeTeamName;
    }

    public String getVisitorTeamName() {
        return visitorTeamName;
    }

    public void setVisitorTeamName(String visitorTeamName) {
        this.visitorTeamName = visitorTeamName;
    }

    public Integer getHomeTeamScore() {
        return homeTeamScore;
    }

    public void setHomeTeamScore(Integer homeTeamScore) {
        this.homeTeamScore = homeTeamScore;
    }

    public Integer getVisitorTeamScore() {
        return visitorTeamScore;
    }

    public void setVisitorTeamScore(Integer visitorTeamScore) {
        this.visitorTeamScore = visitorTeamScore;
    }

    public Map<Player, Integer> getPlayerScores() {
        return playerScores;
    }

    public void setPlayerScores(Map<Player, Integer> playerScores) {
        this.playerScores = playerScores;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    @Override
    public String toString() {
        return "Game{" +
                "date=" + gameDate +
                ", gameId=" + gameId +
                ", homeTeamName='" + homeTeamName + '\'' +
                ", visitorTeamName='" + visitorTeamName + '\'' +
                ", homeTeamScore=" + homeTeamScore +
                ", visitorTeamScore=" + visitorTeamScore +
                ", playerScores=" + playerScores +
                ", commentList=" + commentList +
                '}';
    }


}

package com.hro.exercise.nbachallenge.persistence.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hro.exercise.nbachallenge.util.AppConstants;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * The game model entity
 */
@Entity
@Table(name = "games")
public class Game extends AbstractModel {

    @JsonProperty
    @DateTimeFormat(pattern = AppConstants.DAY_TIME_FORMAT)
    @Temporal(TemporalType.DATE)
    private Date gameDate;

    @Column(name = "gameId", nullable = false)
    @JsonProperty
    private Integer gameId;

    @JsonProperty
    private String homeTeamName;

    @JsonProperty
    private String visitorTeamName;

    @JsonProperty
    private Integer homeTeamScore;

    @JsonProperty
    private Integer visitorTeamScore;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY,
            mappedBy = "game"
    )
    private List<PlayerScores> playerScores = new ArrayList<>();

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY,
            mappedBy = "game"
    )
    private List<Comment> commentList = new ArrayList<>();


    public Date getGameDate() {
        return gameDate;
    }

    public void setGameDate(Date gameDate) {
        this.gameDate = gameDate;
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

    public List<PlayerScores> getPlayerScores() {
        return playerScores;
    }

    public void setPlayerScores(List<PlayerScores> playerScores) {
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

    public void updateComment(Integer commentId, String commentNew) {
        for (Comment c: commentList) {
            if(c.getId() == commentId) {
                c.setComment(commentNew);
                break;
            }
        }
    }

    public void removeComment(Integer commentId) {
        commentList.removeIf(e -> e.getId() == commentId);
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

    @Override
    public boolean equals(Object o) {
        return (o == this) || ((o instanceof Game) && ((((Game) o).gameId) == gameId));
    }
}

package com.hro.exercise.nbachallenge.command;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

/**
 * Game Dto
 * Assures security/filter when providing game data to the user
 */

public class GameDto {

    private Integer id;

    private Integer gameId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date gameDate;
    private String homeTeamName;
    private String visitorTeamName;
    private Integer homeTeamScore;
    private Integer visitorTeamScore;
    @JsonProperty
    private List<PlayerScoresDto> playerScores;
    private List<CommentDto> comments;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
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

    public List<PlayerScoresDto> getPlayerScores() {
        return playerScores;
    }

    public void setPlayerScores(List<PlayerScoresDto> playerScores) {
        this.playerScores = playerScores;
    }

    public List<CommentDto> getComments() {
        return comments;
    }

    public Date getGameDate() {
        return gameDate;
    }

    public void setGameDate(Date gameDate) {
        this.gameDate = gameDate;
    }

    public void setComments(List<CommentDto> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {

        return "GameDto{" +
                "id=" + id +
                ", gameId=" + gameId +
                ", gameDate=" + gameDate +
                ", homeTeamName='" + homeTeamName + '\'' +
                ", visitorTeamName='" + visitorTeamName + '\'' +
                ", homeTeamScore=" + homeTeamScore +
                ", visitorTeamScore=" + visitorTeamScore +
                ", playerScores=" + playerScores.toString() +
                ", comments=" + comments +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (o == this) {return true;}
        if (o instanceof GameDto && ((GameDto) o).gameId == this.gameId) {
            return true;
        }
        return false;
    }
}

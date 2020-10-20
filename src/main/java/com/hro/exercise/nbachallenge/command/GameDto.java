package com.hro.exercise.nbachallenge.command;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Game Dto
 * Assures security/filter when providing game data to the user
 */

public class GameDto {
    @JsonIgnore
    private Integer id;
    @JsonProperty("Game ID")
    private Integer gameId;
    @JsonProperty("Date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date gameDate;
    @JsonProperty("Home Team")
    private String homeTeamName;
    @JsonProperty("Visitor Team")
    private String visitorTeamName;
    @JsonProperty("Home Team Score")
    private Integer homeTeamScore;
    @JsonProperty("Visitor Team Score")
    private Integer visitorTeamScore;
    @JsonProperty("Players that Scored")
    private List<PlayerScoresDto> playerScores;
    @JsonProperty("Comments")
    private List<CommentDto> comments = new ArrayList<>();

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

package com.hro.exercise.nbachallenge.command;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hro.exercise.nbachallenge.util.AppConstants;
import com.sun.istack.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Game Dto
 * Assures security/filter when providing game data to the user
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GameDto {

    @JsonIgnore
    private Integer id;
    private Integer gameId;
    private Date gameDate;
    private String homeTeamName;
    private String visitorTeamName;
    private Integer homeTeamScore;
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

    @JsonProperty("Game ID")
    public Integer getGameId() {
        return gameId;
    }

    @JsonProperty("id")
    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    @JsonProperty("Home Team")
    public String getHomeTeamName() {
        return homeTeamName;
    }

    public void setHomeTeamName(String homeTeamName) {
        this.homeTeamName = homeTeamName;
    }

    @JsonProperty("Visitor Team")
    public String getVisitorTeamName() {
        return visitorTeamName;
    }

    public void setVisitorTeamName(String visitorTeamName) {
        this.visitorTeamName = visitorTeamName;
    }

    @JsonProperty("Home Team Score")
    public Integer getHomeTeamScore() {
        return homeTeamScore;
    }

    @JsonProperty("home_team_score")
    public void setHomeTeamScore(Integer homeTeamScore) {
        this.homeTeamScore = homeTeamScore;
    }

    @JsonProperty("Visitor Team Score")
    public Integer getVisitorTeamScore() {
        return visitorTeamScore;
    }

    @JsonProperty("visitor_team_score")
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

    public void setComments(List<CommentDto> comments) {
        this.comments = comments;
    }

    @NotNull
    @JsonProperty("Date")
    @DateTimeFormat(pattern = AppConstants.DAY_TIME_FORMAT)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = AppConstants.DAY_TIME_FORMAT, timezone = "GMT")
    @Temporal(TemporalType.DATE)
    public Date getGameDate() {
        return gameDate;
    }

    @DateTimeFormat(pattern = AppConstants.DAY_TIME_FORMAT)
    @Temporal(TemporalType.DATE)
    @JsonProperty("date")
    public void setGameDate(Date gameDate) {
        this.gameDate = gameDate;
    }

    @JsonProperty("home_team")
    public void setHomeName(Map<String, String> homeTeam) {
        setHomeTeamName(homeTeam.get("full_name"));
    }

    @JsonProperty("visitor_team")
    public void setVisName(Map<String, String> visitorTeam) {
        setVisitorTeamName(visitorTeam.get("full_name"));
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
        return (o == this) || (o instanceof GameDto && ((GameDto) o).gameId == gameId);
    }
}

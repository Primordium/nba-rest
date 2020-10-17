package com.hro.exercise.nbachallenge.command;


import com.sun.istack.NotNull;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class GameDto {

    @NotNull
    private Integer id;

    private Integer gameId;
    private Date gameDate;
    private String homeTeamName;
    private String visitorTeamName;
    private Integer homeTeamScore;
    private Integer visitorTeamScore;
    private Map<PlayerDto, Integer> playerScores;
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

    public Map<PlayerDto, Integer> getPlayerScores() {
        return playerScores;
    }

    public void setPlayerScores(Map<PlayerDto, Integer> playerScores) {
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
}

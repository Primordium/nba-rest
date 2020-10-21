package com.hro.exercise.nbachallenge.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hro.exercise.nbachallenge.command.GameDto;
import com.hro.exercise.nbachallenge.command.PlayerScoresDto;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class NbaApiParser {

    private final ObjectMapper objectMapper;

    public NbaApiParser(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Integer getGameId(HttpResponse<String> response) throws JsonProcessingException {
        JsonNode rootNode = objectMapper.readTree(response.body());
        return rootNode.path("id").asInt();
    }

    public Date getGameDate(HttpResponse<String> response) throws JsonProcessingException, ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        return format.parse(objectMapper.readTree(response.body()).path("date").asText());
    }

    public String getHomeTeamName(HttpResponse<String> response) throws JsonProcessingException {
        return objectMapper.readTree(response.body()).path("home_team").path("full_name").asText();
    }

    public String getVisitorTeamName(HttpResponse<String> response) throws JsonProcessingException {
        return objectMapper.readTree(response.body()).path("visitor_team").path("full_name").asText();
    }

    public Integer getHomeTeamScore(HttpResponse<String> response) throws JsonProcessingException {
        return objectMapper.readTree(response.body()).path("home_team_score").asInt();
    }

    public Integer getVisitorTeamScore(HttpResponse<String> response) throws JsonProcessingException {
        return objectMapper.readTree(response.body()).path("visitor_team_score").asInt();
    }

    public List<GameDto> getAllStatsByDate(HttpResponse<String> response) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(response.body()).findPath("data");
        List<GameStats> gameStatsList = objectMapper.readValue(jsonNode.toString(), new TypeReference<>() {
        });

        return gameStatsList.stream().map(ele -> {
                    GameDto gameDto = new GameDto();
                    gameDto.setGameId(ele.gameId);
                    gameDto.setHomeTeamName(ele.getHomeTeamName());
                    gameDto.setVisitorTeamName(ele.getVisitorTeamName());
                    gameDto.setHomeTeamScore(ele.getHomeTeamScore());
                    gameDto.setVisitorTeamScore(ele.getVisitorTeamScore());
                    gameDto.setGameDate(ele.getGameDate());
                    return gameDto;
                }).collect(Collectors.toList());
    }

    public List<PlayerScoresDto> getPlayerScores(HttpResponse<String> response) throws IOException {
        JsonNode json = objectMapper.readTree(response.body()).findPath("data");

        List<PlayerAndScore> playerAndScoreList = objectMapper.readValue(json.toString(), new TypeReference<>() {
        });
        System.out.println(playerAndScoreList);
        if(playerAndScoreList.isEmpty()) {
            return null;
        }

        return playerAndScoreList.stream().filter(e -> e.getPts() != null).filter(e -> e.getPts() > 0).map(ele -> {
            PlayerScoresDto playerScoresDto = new PlayerScoresDto();
            playerScoresDto.setFirstName(ele.getFirstName());
            playerScoresDto.setLastName(ele.getLastName());
            playerScoresDto.setScore(ele.getPts());
            return playerScoresDto;
        }).collect(Collectors.toList());

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class GameStats {

        @JsonProperty("id")
        private Integer gameId;

        private String homeTeamName;
        private String visitorTeamName;

        @JsonProperty("home_team_score")
        private Integer homeTeamScore;
        @JsonProperty("visitor_team_score")
        private Integer visitorTeamScore;

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        @Temporal(TemporalType.DATE)
        @JsonProperty("date")
        private Date gameDate;

        @JsonProperty("home_team")
        public void setHomeTeamName(Map<String, String> homeTeam) {
            setHomeTeamName(homeTeam.get("full_name"));
        }
        @JsonProperty("visitor_team")
        public void setVisitorTeamName(Map<String, String> visitorTeam) {
            setVisitorTeamName(visitorTeam.get("full_name"));
        }

        public Integer getGameId() {
            return gameId;
        }

        public Date getGameDate() {
            return gameDate;
        }

        public void setGameDate(Date gameDate) {
            this.gameDate = gameDate;
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

        public Integer getHomeTeamScore() {
            return homeTeamScore;
        }

        public void setHomeTeamScore(Integer homeTeamScore) {
            this.homeTeamScore = homeTeamScore;
        }

        public String getVisitorTeamName() {
            return visitorTeamName;
        }

        public void setVisitorTeamName(String visitorTeamName) {
            this.visitorTeamName = visitorTeamName;
        }

        public Integer getVisitorTeamScore() {
            return visitorTeamScore;
        }

        public void setVisitorTeamScore(Integer visitorTeamScore) {
            this.visitorTeamScore = visitorTeamScore;
        }

        @Override
        public String toString() {
            return "GameStats{" +
                    "gameId=" + gameId +
                    ", homeTeamName='" + homeTeamName + '\'' +
                    ", visitorTeamName='" + visitorTeamName + '\'' +
                    ", homeTeamScore=" + homeTeamScore +
                    ", visitorTeamScore=" + visitorTeamScore +
                    ", gameDate=" + gameDate +
                    '}';
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class PlayerAndScore {

        private String firstName;
        private String lastName;

        @JsonProperty
        private Integer pts;

        @JsonProperty("player")
        private void nestedObject(Map<String, Object> objectMap) {
            setFirstName((String) objectMap.get("first_name"));
            setLastName((String) objectMap.get("last_name"));
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public Integer getPts() {
            return pts;
        }

        public void setPts(Integer pts) {
            this.pts = pts;
        }

        @Override
        public String toString() {
            return "PlayerAndScore{" +
                    "first_name='" + firstName + '\'' +
                    ", last_name='" + lastName + '\'' +
                    ", pts=" + pts +
                    '}';
        }
    }
}

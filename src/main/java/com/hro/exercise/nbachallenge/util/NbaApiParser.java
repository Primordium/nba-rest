package com.hro.exercise.nbachallenge.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hro.exercise.nbachallenge.command.GameDto;
import com.hro.exercise.nbachallenge.command.PlayerScoresDto;
import com.hro.exercise.nbachallenge.persistence.model.PlayerScores;
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

/**
 * Provides specific methods to retrieve values in certain fields of a Json
 */
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

    /**
     * Method to create a list of Game Dtos for a specific date
     *
     * @param response HttpRequest provided by NbaApiParser
     * @return List<GameDto> if present
     * @throws JsonProcessingException
     */
    public List<GameDto> getAllStatsByDate(HttpResponse<String> response) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(response.body()).findPath("data");
        List<GameDto> gameStatsList = objectMapper.readValue(jsonNode.toString(), new TypeReference<>() {
        });

        if(gameStatsList.isEmpty()) {
            return null;
        }

        return gameStatsList;
    }

    /**
     * Method to retrieve the players and their scores, since RapicApi doesn't provide both values
     * in the same object
     *
     * @param response HttpRequest provided by NbaRapidConnection
     * @return List<PlayerScoresDto>
     * @throws IOException
     */
    public List<PlayerScoresDto> getPlayerScores(HttpResponse<String> response) throws IOException {
        JsonNode json = objectMapper.readTree(response.body()).findPath("data");

        List<PlayerScoresDto> playerAndScoreList = objectMapper.readValue(json.toString(), new TypeReference<>() {
        });
        if (playerAndScoreList.isEmpty()) {
            return null;
        }
        return playerAndScoreList.stream()
                .filter(e -> e.getScore() != null)
                .filter(e -> e.getScore() > 0)
                .collect(Collectors.toList());
    }

}

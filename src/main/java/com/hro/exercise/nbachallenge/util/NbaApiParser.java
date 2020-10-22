package com.hro.exercise.nbachallenge.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hro.exercise.nbachallenge.command.GameDto;
import com.hro.exercise.nbachallenge.command.PlayerScoresDto;
import org.springframework.stereotype.Component;

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

    private final ObjectMapper OBJECT_MAPPER;

    public NbaApiParser(ObjectMapper objectMapper) {
        OBJECT_MAPPER = objectMapper;
    }

    public Integer getGameId(HttpResponse<String> response) throws JsonProcessingException {
        JsonNode rootNode = OBJECT_MAPPER.readTree(response.body());

        return rootNode.path("id").asInt();
    }

    public Date getGameDate(HttpResponse<String> response) throws JsonProcessingException, ParseException {
        SimpleDateFormat format = new SimpleDateFormat(AppConstants.DAY_AND_HOUR_TIME_FORMAT);

        format.setTimeZone(TimeZone.getTimeZone("UTC"));

        return format.parse(OBJECT_MAPPER.readTree(response.body()).path("date").asText());
    }

    public String getHomeTeamName(HttpResponse<String> response) throws JsonProcessingException {
        return OBJECT_MAPPER.readTree(response.body()).path("home_team").path("full_name").asText();
    }

    public String getVisitorTeamName(HttpResponse<String> response) throws JsonProcessingException {
        return OBJECT_MAPPER.readTree(response.body()).path("visitor_team").path("full_name").asText();
    }

    public Integer getHomeTeamScore(HttpResponse<String> response) throws JsonProcessingException {
        return OBJECT_MAPPER.readTree(response.body()).path("home_team_score").asInt();
    }

    public Integer getVisitorTeamScore(HttpResponse<String> response) throws JsonProcessingException {
        return OBJECT_MAPPER.readTree(response.body()).path("visitor_team_score").asInt();
    }

    /**
     * Method to create a list of Game Dtos for a specific date
     *
     * @param response HttpRequest provided by NbaApiParser
     * @return List<GameDto> if present
     * @throws JsonProcessingException
     */
    public List<GameDto> getAllStatsByDate(HttpResponse<String> response) throws JsonProcessingException {

        JsonNode jsonNode = OBJECT_MAPPER.readTree(response.body()).findPath("data");
        List<GameDto> gameStatsList = OBJECT_MAPPER.readValue(jsonNode.toString(), new TypeReference<>() {
        });

        return gameStatsList.isEmpty() ? null : gameStatsList;
    }

    /**
     * Method to retrieve the players and their scores, since RapidApi doesn't provide both values
     * in the same object
     *
     * @param response HttpRequest provided by NbaRapidConnection
     * @return List<PlayerScoresDto>
     * @throws IOException
     */
    public List<PlayerScoresDto> getPlayerScores(HttpResponse<String> response) throws IOException {

        JsonNode json = OBJECT_MAPPER.readTree(response.body()).findPath("data");
        List<PlayerScoresDto> playerAndScoreList = OBJECT_MAPPER.readValue(json.toString(), new TypeReference<>() {
        });

        return playerAndScoreList.isEmpty() ? null :
                playerAndScoreList.stream()
                        .filter(e -> e.getScore() != null)
                        .filter(e -> e.getScore() > 0)
                        .collect(Collectors.toList());
    }
}

package com.hro.exercise.nbachallenge.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hro.exercise.nbachallenge.command.GameDto;
import com.hro.exercise.nbachallenge.controller.rest.RestCommentController;
import com.hro.exercise.nbachallenge.controller.rest.RestGameController;
import com.hro.exercise.nbachallenge.exception.*;
import com.hro.exercise.nbachallenge.persistence.model.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.DoubleToIntFunction;
import java.util.stream.Collectors;

@Component
public class RapidApiConnection {

    private final String api_url = "https://rapidapi.p.rapidapi.com/";
    private ObjectMapper objectMapper = new ObjectMapper();
    private NbaApiParser nbaApiParser = new NbaApiParser();
    private static final Logger log = LoggerFactory.getLogger(RestCommentController.class);

    private HttpResponse<String> openNbaApiConnection(String urlSuffix) throws ConfigFileNotFound, ApiConnectionFail {
        ObjectMapper objectMapper = new ObjectMapper();

        Map<?, ?> map = null;
        HttpResponse<String> response = null;

        try {
            map = objectMapper.readValue(Paths.get("src\\main\\resources\\conf\\apiconf.json").toFile(), Map.class);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(api_url + urlSuffix))
                    .header("x-rapidapi-host", "free-nba.p.rapidapi.com")
                    .header("x-rapidapi-key", (String) map.get("api_key"))
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();

            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        } catch (IOException e) {
            throw new ConfigFileNotFound();
        } catch (InterruptedException e) {
            throw new ApiConnectionFail();
        }
        return response;
    }

    public List<GameDto> getGamesByDate(String date) throws ApiConnectionFail, ConfigFileNotFound {
        String url = "games?page='%(pageNumber)'&per_page=100&dates[]=" + date;
        String currentUrl = url.replace("%(pageNumber)", "0");
        HttpResponse<String> response = openNbaApiConnection(currentUrl);
        List<GameDto> gameDtoList = new LinkedList<>();
        try {
            int total_pages = objectMapper.readTree(response.body()).findPath("meta").findPath("total_pages").asInt();
            int currentPage = objectMapper.readTree(response.body()).findPath("meta").findPath("current_page").asInt();
            while (currentPage <= total_pages) {
                gameDtoList.addAll(nbaApiParser.getAllStatsByDate(response));
                currentPage++;
                currentUrl = url.replace("%(pageNumber)", Integer.toString(currentPage));
                response = openNbaApiConnection(currentUrl);
            }
        } catch (JsonProcessingException e) {
            log.warn(new JsonProcessingFailure().getMessage());
        }

        List<GameDto> fullStatsList = new ArrayList<>();
        for (GameDto ele : gameDtoList) {
            GameDto gameById = getGameById(ele.getGameId());
            fullStatsList.add(gameById);
        }
        fullStatsList.removeIf(e -> e.getGameId() == null);

        return fullStatsList;
    }

    public GameDto getGameById(Integer gameId) throws ApiConnectionFail {
        GameDto gameDto = new GameDto();
        try {
            HttpResponse<String> response = openNbaApiConnection("stats?page=0&per_page=100&game_ids[]=" + gameId);
            if(response.statusCode() == 500 || response.statusCode() == 404 || objectMapper.readTree(response.body()).path("data").isEmpty() ) {
                throw new BadApiRequest();
            }
            gameDto.setPlayerScores(nbaApiParser.getPlayerScores(response));
            response = openNbaApiConnection("games/" + gameId);
            gameDto.setGameId(nbaApiParser.getGameId(response));
            gameDto.setHomeTeamName(nbaApiParser.getHomeTeamName(response));
            gameDto.setVisitorTeamName(nbaApiParser.getVisitorTeamName(response));
            gameDto.setHomeTeamScore(nbaApiParser.getHomeTeamScore(response));
            gameDto.setVisitorTeamScore(nbaApiParser.getVisitorTeamScore(response));
            gameDto.setGameDate(nbaApiParser.getGameDate(response));
        } catch (IOException e) {
            log.warn("IOException in RapidApiConnection#getGameById");
            e.printStackTrace();
        } catch (ParseException e) {
            log.warn(new JsonProcessingFailure().getMessage());
        } catch (ConfigFileNotFound configFileNotFound) {
            log.warn(configFileNotFound.getMessage());
        } catch (BadApiRequest badApiRequest) {
            log.warn(badApiRequest.getMessage());
        }
        return gameDto;
    }
}

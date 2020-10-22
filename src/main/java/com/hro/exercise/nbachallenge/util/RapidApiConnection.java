package com.hro.exercise.nbachallenge.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hro.exercise.nbachallenge.command.GameDto;
import com.hro.exercise.nbachallenge.controller.rest.RestCommentController;
import com.hro.exercise.nbachallenge.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
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

@Component
public class RapidApiConnection {

    private final String api_url = "https://rapidapi.p.rapidapi.com/";
    private ObjectMapper objectMapper;
    private NbaApiParser nbaApiParser;
    private static final Logger log = LoggerFactory.getLogger(RestCommentController.class);
    // Was in use, before docker, will try to implement again.
    private File configFile;


    public RapidApiConnection() {
        log.info("Opening api config file");
        // replace file with key for hardcoded key because docker
        //configFile = Paths.get("src\\main\\resources\\conf\\apiconf.json").toFile();
        this.objectMapper = new ObjectMapper();
        this.nbaApiParser = new NbaApiParser(objectMapper);
    }


    /**
     * Opens a connection and retrieves the body of the request done to RapidApi
     * @param urlSuffix used by other methods to search Api for specific data
     * @return HttpResponse with contents of the request
     */

    private HttpResponse<String> openNbaApiConnection(String urlSuffix) {

        Map<?, ?> map = null;
        HttpResponse<String> response = null;
        try {

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(api_url + urlSuffix))
                    .header("x-rapidapi-host", "free-nba.p.rapidapi.com")
                    .header("x-rapidapi-key", "c29e9e9b2fmshe04ede3c49d8164p1215edjsnf93801d1751d")
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();

            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        } catch (IOException e) {
            log.error("Config file not found");
        } catch (InterruptedException e) {
            System.out.println(new ApiConnectionFail().getMessage());
            log.error("Connection to Api has failed");
        }
        return response;
    }

    /**
     * Searches Rapid Api for a specific date, after getting all gameIds
     * invokes getGameById to retrieve the gameDtos
     * @see RapidApiConnection#getGameById(Integer);
     * @param date to search for
     * @return List GameDtos
     */

    public List<GameDto> getGamesByDate(String date) {

        String url = "games?page='%(pageNumber)'&per_page=100&dates[]=" + date;
        String currentUrl = url.replace("%(pageNumber)", "0");
        HttpResponse<String> response = openNbaApiConnection(currentUrl);

        List<GameDto> gameDtoList = new LinkedList<>();

        try {

            if (response.statusCode() == 500 || response.statusCode() == 404 ||
                    objectMapper.readTree(response.body()).path("data").isEmpty()) {
                log.warn("API: Game with " + date + " was not found");
                System.out.println(new BadApiRequest().getMessage());
            }

            int total_pages = objectMapper.readTree(response.body()).findPath("meta").findPath("total_pages").asInt();
            int currentPage = objectMapper.readTree(response.body()).findPath("meta").findPath("current_page").asInt();
            while (currentPage <= total_pages) {
                gameDtoList.addAll(nbaApiParser.getAllStatsByDate(response));
                currentPage++;
                currentUrl = url.replace("%(pageNumber)", Integer.toString(currentPage));
                response = openNbaApiConnection(currentUrl);
            }
        } catch (JsonProcessingException e) {
            log.error(new JsonProcessingFailure().getMessage());
        }

        List<GameDto> fullStatsList = new ArrayList<>();
        for (GameDto ele : gameDtoList) {
            GameDto gameById = getGameById(ele.getGameId());
            fullStatsList.add(gameById);
        }

        // To many requests ??
        // log.info("Api connection done, returning games with " + date + ".");
        fullStatsList.removeIf(e -> e.getGameId() == null);
        return fullStatsList;
    }

    /**
     * Method to search Rapid Api for a gameId return a gameDto
     * @see NbaApiParser
     * @param gameId the id to search for
     * @return gameDto if present null if not
     */
    public GameDto getGameById(Integer gameId) {
        GameDto gameDto = new GameDto();
        HttpResponse<String> response = openNbaApiConnection("stats?page=0&per_page=100&game_ids[]=" + gameId);
        try {
            if (response.statusCode() == 500 || response.statusCode() == 404 || objectMapper.readTree(response.body()).path("data").isEmpty()) {
                log.warn("API: Game with " + gameId + " was not found");
                System.out.println(new BadApiRequest().getMessage());
                return null;
            }
        } catch (JsonProcessingException e) {
            log.error(new JsonProcessingFailure().getMessage());
        }

        try {
            gameDto.setPlayerScores(nbaApiParser.getPlayerScores(response));
            response = openNbaApiConnection("games/" + gameId);
            gameDto.setGameId(nbaApiParser.getGameId(response));
            gameDto.setHomeTeamName(nbaApiParser.getHomeTeamName(response));
            gameDto.setVisitorTeamName(nbaApiParser.getVisitorTeamName(response));
            gameDto.setHomeTeamScore(nbaApiParser.getHomeTeamScore(response));
            gameDto.setVisitorTeamScore(nbaApiParser.getVisitorTeamScore(response));
            gameDto.setGameDate(nbaApiParser.getGameDate(response));
        } catch (IOException | ParseException e) {
            log.error(new JsonProcessingFailure().getMessage());
        }
        return gameDto;
    }
}

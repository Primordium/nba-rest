package com.hro.exercise.nbachallenge.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hro.exercise.nbachallenge.command.GameDto;
import com.hro.exercise.nbachallenge.exception.ApiConnectionFail;
import com.hro.exercise.nbachallenge.exception.BadRapidApiRequest;
import com.hro.exercise.nbachallenge.exception.ErrorMessage;
import com.hro.exercise.nbachallenge.exception.JsonProcessingFailure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Component
public class RapidApiConnection {

    private final ObjectMapper OBJECT_MAPPER;
    private final NbaApiParser NBA_API_PARSER;
    private static final Logger LOG = LoggerFactory.getLogger(RapidApiConnection.class);

    // Was in use, before docker, will try to implement again.
    private File configFile;

    public RapidApiConnection() {
        //LOG.info("Opening api config file");
        // replace file with key for hardcoded key because docker
        //configFile = Paths.get("src\\main\\resources\\conf\\apiconf.json").toFile();
        OBJECT_MAPPER = new ObjectMapper();
        NBA_API_PARSER = new NbaApiParser(OBJECT_MAPPER);
    }

    /**
     * Opens a connection and retrieves the body of the request done to RapidApi
     *
     * @param urlSuffix used by other methods to search Api for specific data
     * @return HttpResponse with contents of the request
     */
    private HttpResponse<String> openNbaApiConnection(String urlSuffix) {
        HttpResponse<String> response = null;

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(AppConstants.API_URL + urlSuffix))
                    .header("x-rapidapi-host", AppConstants.API_HOST)
                    .header("x-rapidapi-key", AppConstants.API_KEY)
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();


            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            if (HttpStatus.valueOf(response.statusCode()) == HttpStatus.INTERNAL_SERVER_ERROR) {
                throw new ApiConnectionFail();
            }

        } catch (IOException e) {
            LOG.error("RAPID API : Config file not found");
        } catch (ApiConnectionFail | InterruptedException apiConnectionFail) {
            LOG.error(apiConnectionFail.getMessage());
        }

        return response;
    }

    /**
     * Searches Rapid Api for a specific date, after getting all gameIds
     * invokes getGameById to retrieve the gameDtos
     *
     * @param date to search for
     * @return List GameDtos
     * @see RapidApiConnection#getGameById(Integer);
     */
    public List<GameDto> getGamesByDate(String date) {
        LOG.info("RAPID API : Searching game with date : '" + date + "'");

        String url = AppConstants.API_SEARCH_BY_DATE + date;
        String currentUrl = url.replace("%(pageNumber)", "0");
        HttpResponse<String> response = openNbaApiConnection(currentUrl);
        List<GameDto> gameDtoList = new LinkedList<>();

        try {
            if ((HttpStatus.valueOf(response.statusCode()) == HttpStatus.INTERNAL_SERVER_ERROR) ||
                    (HttpStatus.valueOf(response.statusCode()) == HttpStatus.NOT_FOUND) ||
                    OBJECT_MAPPER.readTree(response.body()).path("data").isEmpty()) {

                LOG.warn("RAPID API: Did NOT provide games with date '" + date + "'");
                throw new BadRapidApiRequest();
            }

            int total_pages = OBJECT_MAPPER.readTree(response.body()).findPath("meta").findPath("total_pages").asInt();
            int currentPage = OBJECT_MAPPER.readTree(response.body()).findPath("meta").findPath("current_page").asInt();

            while (currentPage++ <= total_pages) {
                gameDtoList.addAll(NBA_API_PARSER.getAllStatsByDate(response));
                currentUrl = url.replace("%(pageNumber)", Integer.toString(currentPage));
                response = openNbaApiConnection(currentUrl);
            }

        } catch (JsonProcessingException e) {
            LOG.error("RAPID API : Provided bad Json object, could not parse");
            LOG.error(new JsonProcessingFailure().getMessage());
        } catch (BadRapidApiRequest badRapidApiRequest) {
            badRapidApiRequest.getMessage();
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
     *
     * @param gameId the id to search for
     * @return gameDto if present null if not
     * @see NbaApiParser
     */
    public GameDto getGameById(Integer gameId) {

        GameDto gameDto = new GameDto();
        HttpResponse<String> response = openNbaApiConnection(AppConstants.API_SEARCH_BY_ID + gameId);

        try {
            if ((HttpStatus.valueOf(response.statusCode()) == HttpStatus.INTERNAL_SERVER_ERROR) ||
                    (HttpStatus.valueOf(response.statusCode()) == HttpStatus.NOT_FOUND) ||
                    OBJECT_MAPPER.readTree(response.body()).path("data").isEmpty()) {

                LOG.warn("RAPID API: Game not found with game id: '" + gameId + "'");

                gameDto = null;
            }
        } catch (JsonProcessingException e) {
            LOG.error(ErrorMessage.JSON_PARSE_PROBLEM);
        }

        if (gameDto != null) {
            try {
                gameDto.setPlayerScores(NBA_API_PARSER.getPlayerScores(response));
                response = openNbaApiConnection("games/" + gameId);
                gameDto.setGameId(NBA_API_PARSER.getGameId(response));
                gameDto.setHomeTeamName(NBA_API_PARSER.getHomeTeamName(response));
                gameDto.setVisitorTeamName(NBA_API_PARSER.getVisitorTeamName(response));
                gameDto.setHomeTeamScore(NBA_API_PARSER.getHomeTeamScore(response));
                gameDto.setVisitorTeamScore(NBA_API_PARSER.getVisitorTeamScore(response));
                gameDto.setGameDate(NBA_API_PARSER.getGameDate(response));
                LOG.info("RAPID API : Found game with game id : '" + gameId + "'");

            } catch (Exception e) {
                LOG.error(ErrorMessage.JSON_PARSE_PROBLEM);
            }
        }
        return gameDto;
    }
}

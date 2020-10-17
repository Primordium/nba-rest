package com.hro.exercise.nbachallenge.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hro.exercise.nbachallenge.command.GameDto;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Map;

@Component
public class RapidApiConnection {

    private final String api_url = "https://rapidapi.p.rapidapi.com/";
    private ObjectMapper objectMapper = new ObjectMapper();
    NbaApiParser nbaApiParser = new NbaApiParser();

    private HttpResponse<String> openNbaApiConnection(String urlSuffix) {
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

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return response;
    }


    public void getAllGames() throws IOException {


/*        System.out.println(response.body());

        NbaApiParser nbaApiParser = new NbaApiParser();
        nbaApiParser.getPlayerScores(response);*/

    }

    public void getGamesByDate(String date) {
        String url = "games?page='%(pageNumber)'&per_page=100&dates[]="+date;
        String currentUrl = url.replace("%(pageNumber)", "0");
        HttpResponse<String> response = openNbaApiConnection(currentUrl);
        try {
            int total_pages = objectMapper.readTree(response.body()).findPath("meta").findPath("total_pages").asInt();
            int currentPage = objectMapper.readTree(response.body()).findPath("meta").findPath("current_page").asInt();
            while(currentPage <= total_pages) {
                nbaApiParser.getAllStatsByDate(response);
                currentPage++;
                currentUrl = url.replace("%(pageNumber)", Integer.toString(currentPage));
                response = openNbaApiConnection(currentUrl);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public GameDto getGameById(Integer gameId) {
        HttpResponse<String> response = openNbaApiConnection("stats?page=0&per_page=100&game_ids[]=" + gameId);
        GameDto gameDto = new GameDto();
        try {
            gameDto.setPlayerScores(nbaApiParser.getPlayerScores(response));
            response = openNbaApiConnection("games/" + gameId);
            gameDto.setGameId(nbaApiParser.getGameId(response));
            gameDto.setHomeTeamName(nbaApiParser.getHomeTeamName(response));
            gameDto.setVisitorTeamName(nbaApiParser.getVisitorTeamName(response));
            gameDto.setHomeTeamScore(nbaApiParser.getHomeTeamScore(response));
            gameDto.setVisitorTeamScore(nbaApiParser.getVisitorTeamScore(response));
            gameDto.setGameDate(nbaApiParser.getGameDate(response));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return gameDto;
    }
}

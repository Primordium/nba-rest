package com.hro.exercise.nbachallenge.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class RapidApiConnection {

    private final String api_url = "https://rapidapi.p.rapidapi.com/";
    private ObjectMapper objectMapper = new ObjectMapper();

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
        NbaApiParser nbaApiParser = new NbaApiParser();
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

    public void getGameById(Integer gameId) {
        HttpResponse<String> response = openNbaApiConnection("stats?page=0&per_page=100&game_ids[]="+gameId);
        System.out.println(response.body());
    }
}

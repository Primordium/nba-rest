package com.hro.exercise.nbachallenge.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Paths;
import java.util.Map;

public class RapidApiConnection {

    public void getAllGames() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Map<?, ?> map = objectMapper.readValue(Paths.get("src\\main\\resources\\conf\\apiconf.json").toFile(), Map.class);


        HttpRequest request = HttpRequest.newBuilder()
               // .uri(URI.create("https://rapidapi.p.rapidapi.com/games/48762"))
                .uri(URI.create("https://rapidapi.p.rapidapi.com/stats?game_ids[]=48762"))
                .header("x-rapidapi-host", "free-nba.p.rapidapi.com")
                .header("x-rapidapi-key", (String) map.get("api_key"))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response;

        {
            try {
                response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
                System.out.println(response.body());

                NbaApiParser nbaApiParser = new NbaApiParser();
                nbaApiParser.getPlayerScores(response);




            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();

            }
        }
    }



}

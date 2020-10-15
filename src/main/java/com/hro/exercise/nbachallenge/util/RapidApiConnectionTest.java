package com.hro.exercise.nbachallenge.util;

import org.springframework.http.converter.json.GsonBuilderUtils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RapidApiConnectionTest {

    public void getAllGames() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://rapidapi.p.rapidapi.com/stats?game_ids[]=48762"))
                .header("x-rapidapi-host", "free-nba.p.rapidapi.com")
                .header("x-rapidapi-key", "c29e9e9b2fmshe04ede3c49d8164p1215edjsnf93801d1751d")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response;

        {
            try {
                response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
                System.out.println(response.body());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

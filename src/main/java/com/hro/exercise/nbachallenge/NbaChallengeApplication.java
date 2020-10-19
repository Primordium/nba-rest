package com.hro.exercise.nbachallenge;

import com.hro.exercise.nbachallenge.exception.NbaChallengeException;
import com.hro.exercise.nbachallenge.persistence.model.Game;
import com.hro.exercise.nbachallenge.persistence.model.PlayerScores;
import com.hro.exercise.nbachallenge.util.RapidApiConnection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.Date;

@SpringBootApplication
public class NbaChallengeApplication {

    public static void main(String[] args) {

        // TODO Test db connection on start

        SpringApplication.run(NbaChallengeApplication.class, args);
        RapidApiConnection rapidApiConnectionTest = new RapidApiConnection();

        try {
            rapidApiConnectionTest.getGamesByDate("2019-03-30");
            Game game = new Game();
            PlayerScores playerScores = new PlayerScores();

        } catch (NbaChallengeException e) {
            e.printStackTrace();
        }
    }


}

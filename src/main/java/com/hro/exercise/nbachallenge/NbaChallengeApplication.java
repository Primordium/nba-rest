package com.hro.exercise.nbachallenge;

import com.hro.exercise.nbachallenge.util.RapidApiConnection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class NbaChallengeApplication {

    public static void main(String[] args) {

        // TODO Test db connection on start

        SpringApplication.run(NbaChallengeApplication.class, args);
        RapidApiConnection rapidApiConnectionTest = new RapidApiConnection();

    }
}



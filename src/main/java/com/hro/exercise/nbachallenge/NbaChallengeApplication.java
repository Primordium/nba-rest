package com.hro.exercise.nbachallenge;

import com.hro.exercise.nbachallenge.util.RapidApiConnection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class NbaChallengeApplication {

	public static void main(String[] args) {

		// TODO Test db connection on start

		SpringApplication.run(NbaChallengeApplication.class, args);
		RapidApiConnection rapidApiConnectionTest = new RapidApiConnection();
		try {
			rapidApiConnectionTest.getAllGames();
			rapidApiConnectionTest.getGamesByDate("2019-03-30");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



}

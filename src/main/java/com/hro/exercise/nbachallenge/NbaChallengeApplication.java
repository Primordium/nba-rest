package com.hro.exercise.nbachallenge;

import com.hro.exercise.nbachallenge.util.RapidApiConnectionTest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NbaChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(NbaChallengeApplication.class, args);
		RapidApiConnectionTest rapidApiConnectionTest = new RapidApiConnectionTest();
		rapidApiConnectionTest.getAllGames();
	}



}

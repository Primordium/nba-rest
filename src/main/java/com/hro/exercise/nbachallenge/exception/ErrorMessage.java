package com.hro.exercise.nbachallenge.exception;

public abstract class ErrorMessage {
    public static final String GAME_NOT_FOUND = "Game does not exist";
    public static final String CANNOT_CONNECT_TO_API = "Error while connecting to API";
    public static final String JSON_PARSE_PROBLEM = "There was a problem parsing the Json provided";
    public static final String BAD_API_REQUEST = "Bad Api Request";
    public static final String CONFIG_FILE_NOT_FOUND = "Missing config file";
}

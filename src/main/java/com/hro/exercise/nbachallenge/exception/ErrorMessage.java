package com.hro.exercise.nbachallenge.exception;

public abstract class ErrorMessage {
    public static final String CANNOT_CONNECT_TO_API = "Error while connecting to API";
    public static final String JSON_PARSE_PROBLEM = "There was a problem parsing the Json provided";
    public static final String BAD_API_REQUEST = "Bad Api Request";
    public static final String COMMENT_NOT_FOUND_WITH_ID = "Comment not found with the ID : ";
    public static final String COMMENT_EMPTY = "Comment cannot be empty";
    public static final String GAME_NOT_FOUND_WITH_ID = "Game not found with ID : ";
    public static final String GAME_NOT_FOUND_WITH_DATE = "Game not found with date : ";
}

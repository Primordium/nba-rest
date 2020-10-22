package com.hro.exercise.nbachallenge.exception;

public class JsonProcessingFailure extends NbaChallengeException {
    public JsonProcessingFailure() {
        super(ErrorMessage.JSON_PARSE_PROBLEM);
    }
}

package com.hro.exercise.nbachallenge.exception;

import com.hro.exercise.nbachallenge.util.Messages;

/**
 * Exception for Json parse
 */
public class JsonProcessingFailure extends NbaChallengeException {
    public JsonProcessingFailure() {
        super(Messages.JSON_PARSE_PROBLEM);
    }
}

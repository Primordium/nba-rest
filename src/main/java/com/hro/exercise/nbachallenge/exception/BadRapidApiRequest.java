package com.hro.exercise.nbachallenge.exception;

public class BadRapidApiRequest extends NbaChallengeException {
    public BadRapidApiRequest() {
        super(ErrorMessage.BAD_API_REQUEST);
    }
}

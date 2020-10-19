package com.hro.exercise.nbachallenge.exception;

public class BadApiRequest extends NbaChallengeException{
    public BadApiRequest() {
        super(ErrorMessage.BAD_API_REQUEST);
    }
}

package com.hro.exercise.nbachallenge.exception;

public class ApiConnectionFail extends NbaChallengeException{
    public ApiConnectionFail() {
        super(ErrorMessage.CANNOT_CONNECT_TO_API);
    }
}

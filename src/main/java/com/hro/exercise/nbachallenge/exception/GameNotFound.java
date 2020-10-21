package com.hro.exercise.nbachallenge.exception;

public class GameNotFound extends NbaChallengeException{
    public GameNotFound() {
        super(ErrorMessage.GAME_NOT_FOUND);
    }
}

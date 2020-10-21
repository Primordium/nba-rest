package com.hro.exercise.nbachallenge.exception;

public class ConfigFileNotFound extends NbaChallengeException{
    public ConfigFileNotFound() {
        super(ErrorMessage.CONFIG_FILE_NOT_FOUND);
    }
}

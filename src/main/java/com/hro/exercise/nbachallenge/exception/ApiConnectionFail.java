package com.hro.exercise.nbachallenge.exception;

import com.hro.exercise.nbachallenge.util.Messages;

/**
 * Exception thrown when Rapid Api connection fails
 */
public class ApiConnectionFail extends NbaChallengeException {
    public ApiConnectionFail() {
        super(Messages.CANNOT_CONNECT_TO_API);
    }
}

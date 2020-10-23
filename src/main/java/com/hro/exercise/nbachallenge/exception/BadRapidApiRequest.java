package com.hro.exercise.nbachallenge.exception;

import com.hro.exercise.nbachallenge.util.Messages;

/**
 * Exception thrown when a bad request is done to Rapid Api
 */
public class BadRapidApiRequest extends NbaChallengeException {
    public BadRapidApiRequest() {
        super(Messages.BAD_API_REQUEST);
    }
}

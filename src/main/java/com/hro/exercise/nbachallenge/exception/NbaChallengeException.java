package com.hro.exercise.nbachallenge.exception;

/**
 * All Exceptions related to processing data extend from
 * NbaChallengeException
 */
public class NbaChallengeException extends Exception {
    public NbaChallengeException(String message) {
        super(message);
    }
}

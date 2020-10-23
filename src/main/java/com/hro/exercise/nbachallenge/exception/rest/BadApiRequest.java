package com.hro.exercise.nbachallenge.exception.rest;

/**
 * Exception thrown when a bad request is done
 */
public class BadApiRequest extends RuntimeException {
    public BadApiRequest(String message) {
        super(message);
    }
}

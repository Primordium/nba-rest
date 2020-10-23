package com.hro.exercise.nbachallenge.exception.rest;

public class BadApiRequest extends RuntimeException {
    public BadApiRequest(String message) {
        super(message);
    }
}

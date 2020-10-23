package com.hro.exercise.nbachallenge.exception.rest;
/**
 * Exception thrown with custom message
 * Bad request type
 */
public class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }
}

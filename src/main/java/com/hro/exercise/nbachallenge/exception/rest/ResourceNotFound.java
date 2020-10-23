package com.hro.exercise.nbachallenge.exception.rest;

/**
 * Exception thrown when Resource is not found
 */
public class ResourceNotFound extends RuntimeException {
        public ResourceNotFound(String message) {
                super(message);
        }
}

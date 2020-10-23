package com.hro.exercise.nbachallenge.exception.rest;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hro.exercise.nbachallenge.util.AppConstants;

import java.time.LocalDateTime;

/**
 * Exception Response format
 */
public class ExceptionResponse {

    private String errorMessage;
    private String errorCode;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = AppConstants.DAY_AND_HOUR_TIME_FORMAT)
    private LocalDateTime timestamp;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

}

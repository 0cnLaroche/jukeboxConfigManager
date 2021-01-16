package com.github.jukeboxConfigManager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown by system when resources returned by API are empty.
 * Returns 500 - INTERNAL_SERVER status code to client
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ApiResourceMissingException extends RuntimeException {

    private final String message;

    public ApiResourceMissingException(final String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

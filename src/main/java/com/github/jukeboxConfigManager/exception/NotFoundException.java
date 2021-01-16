package com.github.jukeboxConfigManager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown by system when a resource could not be found for specified Id.
 * Returns 404 - NOT FOUND status code to client
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException{

    private final String message;

    public NotFoundException(Class<?> clazz, String id) {
        this.message = String.format("No resource %s found for id %s", clazz.getName(), id);
    }

    @Override
    public String getMessage() {
        return message;
    }
}

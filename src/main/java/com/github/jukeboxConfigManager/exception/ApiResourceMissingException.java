package com.github.jukeboxConfigManager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown by system when resources returned by API are empty.
 * Returns 500 - INTERNAL_SERVER status code to client
 */
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ApiResourceMissingException extends RuntimeException implements ApiException {

    private final String message;
    private ErrorCode errorCode;

    public ApiResourceMissingException(ErrorCode errorCode) {
        this.message = "API resource missing";
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public ErrorCode getErrorCode() {
        return this.errorCode;
    }
}

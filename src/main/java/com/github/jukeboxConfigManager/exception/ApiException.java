package com.github.jukeboxConfigManager.exception;

/**
 * API exceptions should implement
 */
public interface ApiException {

    /**
     * Returns the error code to be logged for the exception.
     * @return
     */
    ErrorCode getErrorCode();

    /**
     * Error message
     * @return
     */
    String getMessage();
}

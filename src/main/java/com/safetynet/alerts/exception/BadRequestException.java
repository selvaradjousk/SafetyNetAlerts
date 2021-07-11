package com.safetynet.alerts.exception;

/**
 * Bad Request Exception Class.
 * @author Senthil
 *
 */
public class BadRequestException extends RuntimeException {

    /**
     * Bad Request Exception method.
     * @param message
     */
    public BadRequestException(final String message) {
        super(message);
    }
}

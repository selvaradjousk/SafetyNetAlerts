package com.safetynet.alerts.exception;

/**
 * Data Already Registered Exception Class.
 * @author Senthil
 *
 */
public class DataAlreadyRegisteredException extends RuntimeException {

	/**
	 * Data Already Registered Exception method.
	 *
	 * @param message the message
	 */
    public DataAlreadyRegisteredException(final String message) {
        super(message);
    }
}


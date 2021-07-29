package com.safetynet.alerts.exception;

/**
 * Data Data Not Found Exception Class.
 * @author Senthil
 *
 */
public class DataNotFoundException extends RuntimeException {

	/**
	 * Data Data Not Found Exception method.
	 *
	 * @param message the message
	 */
    public DataNotFoundException(final String message) {
        super(message);
    }
}

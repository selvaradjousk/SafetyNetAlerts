package com.safetynet.alerts.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Exception Details Class.
 * @author Senthil
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class ExceptionDetails {

	/**
	 * Exception  error timestamp.
	 */
	private LocalDateTime timestamp;

	/**
	 * Exception  error message.
	 */
	private String message;

	/**
	 * Exception  error details.
	 */
	private String details;

}

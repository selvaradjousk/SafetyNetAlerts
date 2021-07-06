package com.safetynet.alerts.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ExceptionDetails {
	private LocalDateTime timestamp;
	private String message;
	private String details;

}

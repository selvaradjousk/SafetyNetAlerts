package com.safetynet.alerts.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Exception Handlers Class.
 * @author Senthil
 *
 */
@ControllerAdvice
public class ExceptionHandlers extends ResponseEntityExceptionHandler {

    /**
     * Handle Bad Request method.
     *
     * @param ex the ex
     * @param request the request
     * @return  HTTP status
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity handleBadRequest(
    		final BadRequestException ex,
    		final WebRequest request) {

        ExceptionDetails exceptionDetails = new ExceptionDetails(
        		LocalDateTime.now(),
        		ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle Not Found.
     *
     * @param ex the ex
     * @param request the request
     * @return HTTP status
     */
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity handleNotFound(
    		final DataNotFoundException ex,
    		final WebRequest request) {

    	ExceptionDetails exceptionDetails = new ExceptionDetails(
    			LocalDateTime.now(),
    			ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(exceptionDetails, HttpStatus.NOT_FOUND);
    }

    /**
     * Handle Conflict.
     *
     * @param ex the ex
     * @param request the request
     * @return HTTP conflict status
     */
    @ExceptionHandler(DataAlreadyRegisteredException.class)
    public ResponseEntity handleConflict(
    		final DataAlreadyRegisteredException ex,
    		final WebRequest request) {

    	ExceptionDetails exceptionDetails = new ExceptionDetails(
        		LocalDateTime.now(),
        		ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(exceptionDetails, HttpStatus.CONFLICT);
    }
}

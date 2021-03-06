package com.safetynet.alerts.exception;

import java.time.LocalDateTime;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.log4j.Log4j2;

/**
 * Exception Handlers Class.
 * @author Senthil
 *
 */
@Log4j2
@ControllerAdvice
public class ExceptionHandlers extends ResponseEntityExceptionHandler {

    /**
     * Handle constraint violation exception.
     *
     * @param ex the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity handleConstraintViolationException(
    		final ConstraintViolationException ex,
    		final WebRequest request) {
    	log.error("Exception occured as Request Failed: {}", ex);
        ExceptionDetails exceptionDetails = new ExceptionDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }

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
    	log.error("Exception occured as Request Failed: {}", ex);
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
    	log.error("Exception occured as Request Failed: {}", ex);
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
    	log.error("Exception occured as Request Failed: {}", ex);
    	ExceptionDetails exceptionDetails = new ExceptionDetails(
        		LocalDateTime.now(),
        		ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(exceptionDetails, HttpStatus.CONFLICT);
    }
}

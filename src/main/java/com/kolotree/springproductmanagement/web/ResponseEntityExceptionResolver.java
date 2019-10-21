package com.kolotree.springproductmanagement.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.format.DateTimeParseException;

@ControllerAdvice
public class ResponseEntityExceptionResolver extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseEntityExceptionResolver.class);

    @ExceptionHandler({IllegalArgumentException.class, DateTimeParseException.class})
    public ResponseEntity<String> handleBadRequestExceptions(HttpServletRequest webRequest, Exception e) {
        LOGGER.debug("Request {} {} failed with message {}", webRequest.getMethod(), webRequest.getRequestURI(), e.getMessage());
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handle(Exception e) {
        LOGGER.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Sorry, something went wrong");
    }
}

package com.qa.blog.springweb.exception;

import com.qa.blog.core.exception.BlogException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BlogException.class)
    public ResponseEntity<ErrorDetails> handleApiException(BlogException blogException) {
        logger.atInfo().log(blogException.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDetails(blogException.getErrorCode(), blogException.getErrorMessage()));
    }
}
package com.qa.blog.core.exception;

public class BlogException extends RuntimeException {
    private final String errorCode;
    private final String errorMessage;

    public BlogException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}

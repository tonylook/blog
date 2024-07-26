package com.qa.blog.core.exception;

public enum BlogErrorCode {
    CONTENT_TOO_LONG("BR-1", "The content cannot be longer than 1024 characters"),
    TITLE_REQUIRED("BR-2", "Title is required");


    private final String code;
    private final String message;

    BlogErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
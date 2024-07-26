package com.qa.blog.core.exception;

public class PostNotFoundException extends RuntimeException {

    public PostNotFoundException(String exception) {
        super(exception);
    }
}

package com.learning.exceptions;

public class UserServiceException extends RuntimeException {

    private static final long serialVersionUID = 1913189181425972323L;

    public UserServiceException(String message) {
        super(message);
    }
}

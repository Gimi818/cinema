package com.cinema.common.exception.exceptions;

public class TooManyException extends RuntimeException {

    public TooManyException(final String message) {
        super(message);
    }
}

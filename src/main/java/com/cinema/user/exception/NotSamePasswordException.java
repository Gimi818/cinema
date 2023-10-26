package com.cinema.user.exception;

public class NotSamePasswordException extends RuntimeException {
    public NotSamePasswordException() {
        super("The passwords given aren't the same ");

    }
}

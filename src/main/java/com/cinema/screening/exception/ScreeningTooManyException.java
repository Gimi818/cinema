package com.cinema.screening.exception;

public class ScreeningTooManyException extends RuntimeException{
    public ScreeningTooManyException() {
        super("It is impossible to add another screening because there are already three");

    }
}

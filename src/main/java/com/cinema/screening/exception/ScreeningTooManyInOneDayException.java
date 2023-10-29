package com.cinema.screening.exception;

public class ScreeningTooManyInOneDayException extends RuntimeException{
    public ScreeningTooManyInOneDayException() {
        super("It is impossible to add another screening because there are already five");

    }
}

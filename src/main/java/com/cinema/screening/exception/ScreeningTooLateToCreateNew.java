package com.cinema.screening.exception;

public class ScreeningTooLateToCreateNew extends RuntimeException {
    public ScreeningTooLateToCreateNew() {
        super("It isn't possible to create new screening earlier than the current time.");

    }
}

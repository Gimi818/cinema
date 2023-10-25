package com.cinema.screening.exception;

import java.time.LocalDate;

public class ScreeningTimeDifferenceException extends RuntimeException{
    public ScreeningTimeDifferenceException() {
        super("Time difference is too small to start a new screening ,the minimum time difference must be at least 4 hours.");

    }
}


package com.cinema.screening.exception;

import java.time.LocalDate;

public class ScreeningTimeDifferenceException extends RuntimeException{
    public ScreeningTimeDifferenceException() {
        super("Time difference is too small to start a new screening.");

    }
}


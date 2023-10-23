package com.cinema.screening.exception;

import java.time.LocalDate;

public class ScreeningNotFoundException  extends RuntimeException{
    public ScreeningNotFoundException(LocalDate date) {
        super(String.format("Screening with date %s not found", date));

    }
}

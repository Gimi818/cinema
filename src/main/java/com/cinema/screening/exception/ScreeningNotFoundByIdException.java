package com.cinema.screening.exception;

import java.time.LocalDate;

public class ScreeningNotFoundByIdException extends  RuntimeException{
    public ScreeningNotFoundByIdException(Long id) {
        super(String.format("Screening with id %d not found", id));

    }
}

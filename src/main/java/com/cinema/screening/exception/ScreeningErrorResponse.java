package com.cinema.screening.exception;

import org.springframework.http.HttpStatus;

public record ScreeningErrorResponse(String message, HttpStatus status) {
}

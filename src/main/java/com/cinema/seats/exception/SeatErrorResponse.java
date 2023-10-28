package com.cinema.seats.exception;

import org.springframework.http.HttpStatus;

public record SeatErrorResponse(String message, HttpStatus status) {
}

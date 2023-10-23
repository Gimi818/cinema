package com.cinema.film.exception;

import org.springframework.http.HttpStatus;

public record FilmErrorResponse(String message, HttpStatus status) {
}

package com.cinema.user.exception;

import org.springframework.http.HttpStatus;

public record UserErrorResponse (String message, HttpStatus status) {
}

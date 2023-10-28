package com.cinema.seats.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;


public record SeatErrorResponse(String message, HttpStatus status) {
}

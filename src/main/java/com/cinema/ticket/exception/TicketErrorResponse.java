package com.cinema.ticket.exception;

import org.springframework.http.HttpStatus;

public record TicketErrorResponse(String message, HttpStatus status) {
}

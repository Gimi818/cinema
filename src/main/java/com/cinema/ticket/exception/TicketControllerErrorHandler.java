package com.cinema.ticket.exception;

import com.cinema.film.exception.FilmErrorResponse;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Log4j2
public class TicketControllerErrorHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(TicketNotFoundException.class)
    @ResponseBody
    public TicketErrorResponse TicketNotFoundException(TicketNotFoundException exception) {
        final String message = exception.getMessage();
        log.error(message);
        return new
                TicketErrorResponse(message, HttpStatus.NOT_FOUND);
    }
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(TooLateToBookException.class)
    @ResponseBody
    public TicketErrorResponse TooLateToBookException(TooLateToBookException exception) {
        final String message = exception.getMessage();
        log.error(message);
        return new
                TicketErrorResponse(message, HttpStatus.CONFLICT);
    }
}

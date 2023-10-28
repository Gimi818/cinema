package com.cinema.seats.exception;

import com.cinema.ticket.exception.TicketErrorResponse;
import com.cinema.ticket.exception.TicketNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
@ControllerAdvice
@Log4j2
public class SeatControllerErrorhandler {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(SeatAlreadyTakenException.class)
    @ResponseBody
    public SeatErrorResponse SeatAlreadyTakenException(SeatAlreadyTakenException exception) {
        final String message = exception.getMessage();
        log.error(message);
        return new
                SeatErrorResponse(message, HttpStatus.CONFLICT);
    }



    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(SeatNotFoundException.class)
    @ResponseBody
    public SeatErrorResponse SeatNotFoundException(SeatNotFoundException exception) {
        final String message = exception.getMessage();
        log.error(message);
        return new
                SeatErrorResponse(message, HttpStatus.NOT_FOUND);
    }

}

package com.cinema.screening.exception;

import com.cinema.film.exception.FilmErrorResponse;

import com.cinema.film.exception.FilmNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
@ControllerAdvice
@Log4j2
public class ScreeningControllerErrorHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ScreeningNotFoundByIdException.class)
    @ResponseBody
    public FilmErrorResponse ScreeningNotFoundByIdException(ScreeningNotFoundByIdException exception) {
        final String message = exception.getMessage();
        log.error(message);
        return new
                FilmErrorResponse(message, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ScreeningNotFoundException.class)
    @ResponseBody
    public FilmErrorResponse ScreeningNotFound(ScreeningNotFoundException exception) {
        final String message = exception.getMessage();
        log.error(message);
        return new
                FilmErrorResponse(message, HttpStatus.NOT_FOUND);
    }
}

package com.cinema.film.exception;


import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
@ControllerAdvice
@Log4j2
public class FilmControllerErrorHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(FilmNotFoundException.class)
    @ResponseBody
    public FilmErrorResponse filmNotFound(FilmNotFoundException exception) {
        final String message = exception.getMessage();
        log.error(message);
        return new
                FilmErrorResponse(message, HttpStatus.NOT_FOUND);
    }
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(FilmExistByTitleException.class)
    @ResponseBody
    public FilmErrorResponse filmExistByTitle (FilmExistByTitleException exception) {
        final String message = exception.getMessage();
        log.error(message);
        return new
                FilmErrorResponse(message, HttpStatus.CONFLICT);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FilmCategoryFormatException.class)
    @ResponseBody
    public FilmErrorResponse badCategoryFormat(FilmCategoryFormatException exception) {
        final String message = exception.getMessage();
        log.error(message);
        return new
                FilmErrorResponse(message, HttpStatus.BAD_REQUEST);
    }
}

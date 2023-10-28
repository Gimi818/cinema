package com.cinema.user.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Log4j2
public class UserControllerErrorHandler {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(EmailAlreadyExistsException.class)
    @ResponseBody
    public UserErrorResponse EmailAlreadyExistsException(EmailAlreadyExistsException exception) {
        final String message = exception.getMessage();
        log.error(message);
        return new
                UserErrorResponse(message, HttpStatus.CONFLICT);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(NotSamePasswordException.class)
    @ResponseBody
    public UserErrorResponse NotSamePasswordException(NotSamePasswordException exception) {
        final String message = exception.getMessage();
        log.error(message);
        return new
                UserErrorResponse(message, HttpStatus.CONFLICT);
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundByIdException.class)
    @ResponseBody
    public UserErrorResponse UserNotFoundByIdException(UserNotFoundByIdException exception) {
        final String message = exception.getMessage();
        log.error(message);
        return new
                UserErrorResponse(message, HttpStatus.NOT_FOUND);
    }


}

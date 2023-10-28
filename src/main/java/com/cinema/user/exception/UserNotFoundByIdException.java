package com.cinema.user.exception;

public class UserNotFoundByIdException extends RuntimeException{
    public UserNotFoundByIdException(Long userId) {
        super(String.format("User with id %d not found", userId));

    }
}

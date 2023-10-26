package com.cinema.user.exception;

public class EmailAlreadyExistsException extends RuntimeException{


    public EmailAlreadyExistsException(String mail) {
        super(String.format("This email %s is already taken", mail));

    }
}

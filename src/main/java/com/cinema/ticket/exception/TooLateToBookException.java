package com.cinema.ticket.exception;

public class TooLateToBookException extends RuntimeException{
    public TooLateToBookException() {
        super(String.format("Too late to book a ticket"));

    }
}

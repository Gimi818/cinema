package com.cinema.seats.exception;

public class SeatAlreadyTakenException extends RuntimeException{
    public SeatAlreadyTakenException() {
        super(String.format("This seat is already taken."));

    }
}

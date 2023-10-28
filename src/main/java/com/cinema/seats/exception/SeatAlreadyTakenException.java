package com.cinema.seats.exception;

public class SeatAlreadyTakenException extends RuntimeException{
    public SeatAlreadyTakenException(int row , int seat) {
        super(String.format("This seat %d %d is already taken.", row ,seat));

    }
}

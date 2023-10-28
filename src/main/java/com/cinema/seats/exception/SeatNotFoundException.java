package com.cinema.seats.exception;

public class SeatNotFoundException extends RuntimeException {
    public SeatNotFoundException(int row , int seat) {
        super(String.format("Seat not found for provided row /d and seat number /d.", row ,seat));

    }
}

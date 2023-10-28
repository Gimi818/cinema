package com.cinema.screening.dto;

import com.cinema.seats.Seat;

import java.util.List;

public record ScreeningAvailableSeats(Long id ,List<Seat> seats) {
}

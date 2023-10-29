package com.cinema.screening.dto;

import com.cinema.seats.Seat;

import java.util.List;

public record ScreeningAvailableSeats(List<Seat> seats) {
}

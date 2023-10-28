package com.cinema.screening.dto;

import com.cinema.seats.Seat;
import com.cinema.film.Film;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


public record ScreeningResponseDto(Long id , LocalDate date, LocalTime time, Film film) {
}

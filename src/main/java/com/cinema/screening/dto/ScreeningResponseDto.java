package com.cinema.screening.dto;

import com.cinema.film.Film;

import java.time.LocalDate;
import java.time.LocalTime;


public record ScreeningResponseDto(Long id, LocalDate date, LocalTime time, Film film) {
}

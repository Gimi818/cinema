package com.cinema.screening.dto;

import com.cinema.film.Film;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;
import java.time.LocalTime;

public record CreatedScreeningDto(Long id, LocalDate date, LocalTime time, Film film) {

}

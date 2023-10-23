package com.cinema.film.dto;

import com.cinema.film.filmCategory.FilmCategory;

public record FilmResponseDto(Long id, String title, FilmCategory category, int durationFilmInMinutes) {
}

package com.cinema.film.dto;

import com.cinema.film.filmCategory.FilmCategory;
public record FilmRequestDto ( String title , FilmCategory category, int durationFilmInMinutes){
}

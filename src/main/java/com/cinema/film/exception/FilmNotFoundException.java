package com.cinema.film.exception;

public class FilmNotFoundException extends RuntimeException {
    public FilmNotFoundException(Long filmId) {
        super(String.format("Film with id %d not found", filmId));

    }
}

package com.cinema.film;

import com.cinema.film.dto.CreatedFilmDto;
import com.cinema.film.dto.FilmRequestDto;
import com.cinema.film.dto.FilmResponseDto;
import com.cinema.film.filmCategory.FilmCategory;
import java.util.List;
public interface FilmFacade {


    CreatedFilmDto saveFilm(FilmRequestDto filmRequestDto);

    FilmResponseDto findFilmById(Long id);
    Film findById(Long id);

    List<FilmResponseDto> findAllFilms();

    List<FilmResponseDto> findFilmByCategory(FilmCategory filmCategory);

    void deleteFilm(Long id);
}


package com.cinema.film;

import com.cinema.film.exception.FilmExistByTitleException;
import com.cinema.film.dto.FilmRequestDto;
import com.cinema.film.dto.FilmResponseDto;
import com.cinema.film.exception.FilmNotFoundException;
import com.cinema.film.filmCategory.FilmCategory;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Log4j2
public class FilmService {

    private final FilmRepository repository;
    private final FilmMapper mapper;
    private final FilmValidation validation;

    public Film saveFilm(FilmRequestDto filmRequestDto) {
        validation.existByTitle(filmRequestDto);
        log.info("Saving film {}", filmRequestDto);
        Film film = repository.save(mapper.dtoToEntity(filmRequestDto));
        log.info("Saved film");
        return film;
    }

    public FilmResponseDto findFilmById(Long id) {
        log.info("Finding film with ID {}", id);
        Film film = repository.findById(id).orElseThrow(() -> new FilmNotFoundException(id));
        log.info("Found film {}", film);
        return mapper.entityToDto(film);
    }

    public List<FilmResponseDto> findAllFilms() {
        log.info("Returning all films");
        return repository.findAll().stream()
                .map(mapper::entityToDto)
                .collect(Collectors.toList());
    }

    public List<FilmResponseDto> findFilmByCategory(FilmCategory filmCategory) {
        List<Film> films = repository.findByCategory(filmCategory);
        log.info("Found {} films.", films.size());
        log.info("Returning all films by category {}", filmCategory);
        return films.stream()
                .map(mapper::entityToDto)
                .collect(Collectors.toList());
    }

    public void deleteFilm(Long id) {
        Film film = repository.findById(id).orElseThrow(()-> new FilmNotFoundException(id));
        log.info("Film with id {} deleted", id);
        repository.delete(film);
    }


}

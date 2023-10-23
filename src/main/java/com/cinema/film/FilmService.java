package com.cinema.film;

import com.cinema.film.exception.FilmCategoryFormatException;
import com.cinema.film.exception.FilmExistByTitleException;
import com.cinema.film.dto.FilmRequestDto;
import com.cinema.film.dto.FilmResponseDto;
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


    public Film saveFilm(FilmRequestDto filmRequestDto) {
        if (repository.existsByTitle(filmRequestDto.title())) {
            throw new FilmExistByTitleException(filmRequestDto.title());
        }
        log.info("Saving film {}", filmRequestDto);
        Film film = repository.save(mapper.dtoToEntity(filmRequestDto));
        log.info("Saved film");
        return film;
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
        Film film = repository.findById(id).orElseThrow();
        log.info("Film with id {} deleted",id);
        repository.delete(film);
    }


}

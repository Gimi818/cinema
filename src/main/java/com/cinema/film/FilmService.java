package com.cinema.film;

import com.cinema.exceptions.NotFoundException;
import com.cinema.film.dto.CreatedFilmDto;
import com.cinema.film.dto.FilmRequestDto;
import com.cinema.film.dto.FilmResponseDto;
import com.cinema.film.exception.FilmExistByTitleException;
import com.cinema.film.exception.FilmNotFoundException;
import com.cinema.film.filmCategory.FilmCategory;
import jakarta.transaction.Transactional;
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
    @Transactional
    public CreatedFilmDto saveFilm(FilmRequestDto filmRequestDto) {
        existByTitle(filmRequestDto);
        log.info("Saving film {}", filmRequestDto);
        Film film = repository.save(mapper.dtoToEntity(filmRequestDto));
        log.info("Saved film");
        return mapper.createdEntityToDto(film);
    }

    public FilmResponseDto findFilmById(Long id) {
        log.info("Finding film with ID {}", id);
        Film film = repository.findById(id).orElseThrow(() -> new NotFoundException( id));
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
    @Transactional
    public void deleteFilm(Long id) {
        Film film = repository.findById(id).orElseThrow(() -> new NotFoundException(id));
        log.info("Film with id {} deleted", id);
        repository.deleteById(id);
    }

    public void existByTitle (FilmRequestDto requestDto){
        if (repository.existsByTitle(requestDto.title())) {
            throw new FilmExistByTitleException(requestDto.title());
        }
    }
    static final class ErrorMessages {
        static final String FILM_NOT_EXIST = "Account not exist";
        static final String ACCOUNT_ROLE_NOT_EXIST = "Account role not exist";
        static final String ACCOUNT_FOUND_BY_LOGIN = "Account was found";
    }
}

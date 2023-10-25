package com.cinema.film;

import com.cinema.film.dto.FilmRequestDto;
import com.cinema.film.exception.FilmExistByTitleException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class FilmValidation {

    private final FilmRepository repository;

    public void existByTitle (FilmRequestDto requestDto){
        if (repository.existsByTitle(requestDto.title())) {
            throw new FilmExistByTitleException(requestDto.title());
        }
    }
}


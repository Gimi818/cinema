package com.cinema.film;

import com.cinema.film.dto.FilmRequestDto;
import com.cinema.film.exception.FilmExistByTitleException;
import com.cinema.film.filmCategory.FilmCategory;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@SpringBootTest
class FilmValidationTest {
    @Mock
    private FilmRepository repository;

    @InjectMocks
    private FilmValidation filmValidation;

    @Test
    void existByTitle_FilmExists_ThrowsFilmExistByTitleException() {
        // Given
        FilmRequestDto requestDto = new FilmRequestDto("TOP GUN", FilmCategory.HORROR,122);

        when(repository.existsByTitle("TOP GUN")).thenReturn(true);

        // Then
        assertThrows(FilmExistByTitleException.class, () ->
                filmValidation.existByTitle(requestDto)
        );
    }

    @Test
    void existByTitle_FilmDoesNotExist_NoExceptionsThrown() {
        // Given
        String nonExistingTitle = "AABBCC";
        FilmRequestDto requestDto = new FilmRequestDto(nonExistingTitle,FilmCategory.HORROR,110);

        when(repository.existsByTitle(nonExistingTitle)).thenReturn(false);

        // Then
        assertDoesNotThrow(() ->
                filmValidation.existByTitle(requestDto)
        );
    }
}

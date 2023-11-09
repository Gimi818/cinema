package com.cinema.film;

import com.cinema.film.dto.FilmRequestDto;
import com.cinema.film.exception.FilmExistByTitleException;
import com.cinema.film.filmCategory.FilmCategory;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("Should throw exception when film exist by title ")
    void should_throw_exception() {
        // Given
        FilmRequestDto requestDto = new FilmRequestDto("TOP GUN", FilmCategory.HORROR,122);

        when(repository.existsByTitle("TOP GUN")).thenReturn(true);

        // Then
        assertThrows(FilmExistByTitleException.class, () ->
                filmValidation.existByTitle(requestDto)
        );
    }

    @Test
    @DisplayName("shouldn't throw  exception when film doesn't exist  by title")
    void should_not_throw_exception() {
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

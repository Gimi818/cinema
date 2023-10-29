package com.cinema.film;

import com.cinema.film.dto.FilmRequestDto;
import com.cinema.film.dto.FilmResponseDto;
import com.cinema.film.exception.FilmNotFoundException;
import com.cinema.film.filmCategory.FilmCategory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.cinema.film.filmCategory.FilmCategory.ACTION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static com.cinema.film.filmCategory.FilmCategory.FANTASY;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FilmServiceTest {

    @Mock
    private FilmRepository filmRepository;
    @Mock
    private FilmMapper filmMapper;
    @InjectMocks
    private FilmService service;
    @Mock
    private FilmRequestDto filmRequestDto;
    @Mock
    private FilmResponseDto filmResponseDto;
    @Mock
    private FilmResponseDto secoundFilmResponseDto;
    @Mock
    private Film film;
    @Mock
    private Film secoundFilm;
    @Mock
    private FilmValidation filmValidation;
    @Mock
    private FilmCategory filmCategory;


    @BeforeEach
    void setUp() {
        filmRequestDto = new FilmRequestDto("Harry Potter", FANTASY, 130);
        film = new Film(1L, "Harry Potter", FANTASY, 130);
        secoundFilm = new Film(2L, "John Wick", ACTION, 110);
    }

    @Test
    @DisplayName("Should save film")
    void should_save_film() {
        given(filmRepository.save(filmMapper.dtoToEntity(filmRequestDto)))
                .willReturn(film);
        assertThat(service.saveFilm(filmRequestDto))
                .isEqualTo(film);
    }


    @Test
    @DisplayName("Should find all films by category")
    void should_find_all_films_by_category() {
        filmResponseDto = new FilmResponseDto(1L, "Harry Potter", ACTION, 130);

        List<Film> filmsList = List.of(film);
        List<FilmResponseDto> expectedFilmsDtoList = List.of(filmResponseDto);

        given(filmRepository.findByCategory(FANTASY)).willReturn(filmsList);
        given(filmMapper.entityToDto(film)).willReturn(filmResponseDto);


        List<FilmResponseDto> actualFilmsDtoList = service.findFilmByCategory(FANTASY);
        Assertions.assertThat(expectedFilmsDtoList).isEqualTo(actualFilmsDtoList);
        Mockito.verify(filmMapper, Mockito.times(1)).entityToDto(film);
    }

    @Test
    @DisplayName("Should find all films")
    void should_find_all_films() {
        filmResponseDto = new FilmResponseDto(1L, "Harry Potter", FANTASY, 130);
        secoundFilmResponseDto = new FilmResponseDto(2L, "John Wick", ACTION, 110);

        List<Film> filmsList = List.of(film, secoundFilm);
        List<FilmResponseDto> expectedFilmsDtoList = List.of(filmResponseDto, secoundFilmResponseDto);

        given(filmRepository.findAll()).willReturn(filmsList);
        given(filmMapper.entityToDto(film)).willReturn(filmResponseDto);
        given(filmMapper.entityToDto(secoundFilm)).willReturn(secoundFilmResponseDto);

        List<FilmResponseDto> actualFilmsDtoList = service.findAllFilms();

        Assertions.assertThat(expectedFilmsDtoList).isEqualTo(actualFilmsDtoList);
        Mockito.verify(filmMapper, Mockito.times(1)).entityToDto(film);
        Mockito.verify(filmMapper, Mockito.times(1)).entityToDto(secoundFilm);
    }

    @Test
    @DisplayName("Should throw CarNotFoundException when client is not found")
    void should_throw_CarNotFoundException() {
        // Given
        Long carId = 1L;
        //when
      //  when(filmRepository.findByCategory(ACTION)).thenReturn(Optional.empty());
        //Then
        assertThrows(FilmNotFoundException.class, () -> service.findFilmByCategory(ACTION));
    }
}

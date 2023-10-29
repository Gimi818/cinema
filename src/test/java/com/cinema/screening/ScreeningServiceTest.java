package com.cinema.screening;

import com.cinema.film.Film;
import com.cinema.film.FilmMapper;
import com.cinema.film.FilmRepository;
import com.cinema.film.FilmService;
import com.cinema.film.dto.FilmRequestDto;
import com.cinema.film.dto.FilmResponseDto;
import com.cinema.screening.dto.ScreeningRequestDto;
import com.cinema.screening.dto.ScreeningResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import static com.cinema.film.filmCategory.FilmCategory.FANTASY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ScreeningServiceTest {
    @Mock
    private ScreeningRepository screeningRepository;
    @Mock
    private ScreeningMapper screeningMapper;
    @InjectMocks
    private ScreeningService service;
    @Mock
    private ScreeningRequestDto screeningRequestDto;
    @Mock
    private ScreeningResponseDto screeningResponseDto;
    @Mock
    private ScreeningResponseDto secoundScreeningResponseDto;
    @Mock
    private Screening screening;
    @Mock
    private Screening secoundScreening;
    @Mock
    private Film film;
    @Mock
    private FilmRepository filmRepository;
    @Mock
    private ScreeningValidate screeningValidate;


    @BeforeEach
    void setUp() {
        LocalDate date = LocalDate.of(2023, 12, 31);
        LocalTime time = LocalTime.of(12, 30, 00);
        screeningRequestDto = new ScreeningRequestDto(date, time);
        screening = new Screening(1L, date, time, film, new ArrayList<>());
      film = new Film(1L, "Harry Potter", FANTASY, 130);



    }

    @Test
    @DisplayName("Should save screeing")
    void should_save_film() {
        film.setId(1L);
        given(screeningRepository.save(screeningMapper.dtoToEntity(screeningRequestDto)))
                .willReturn(screening);
        assertThat(service.saveScreening(screeningRequestDto,film.getId()))
                .isEqualTo(screening);
    }
}

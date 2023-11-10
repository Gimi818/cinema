package com.cinema.screening;

import com.cinema.film.*;
import com.cinema.film.dto.FilmRequestDto;
import com.cinema.film.dto.FilmResponseDto;
import com.cinema.screening.dto.ScreeningRequestDto;
import com.cinema.screening.dto.ScreeningResponseDto;
import com.cinema.seats.Seat;
import com.cinema.seats.SeatService;
import com.cinema.seats.SeatStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.cinema.film.filmCategory.FilmCategory.ACTION;
import static com.cinema.film.filmCategory.FilmCategory.FANTASY;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.BDDMockito.given;

@SpringBootTest
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
    @Mock
    private SeatService seatService;


    @BeforeEach
    void setUp() {
        Film film = new Film(1L, "Harry Potter", FANTASY, 130);
        MockitoAnnotations.initMocks(this);
        LocalDate date = LocalDate.of(2023, 12, 31);
        LocalTime time = LocalTime.of(12, 10);
        screeningRequestDto = new ScreeningRequestDto(date, time);
        screening = new Screening(1L, date, time, film, new ArrayList<>());
        secoundScreening = new Screening(2L, date, time, film, new ArrayList<>());

    }
//    @Test
//    @DisplayName("Should save screening")
//    void should_save_film() {
//        Film film = new Film(1L, "Harry Potter", FANTASY, 130);
//
//        given(filmRepository.findById(1L)).willReturn(Optional.of(film));
//
//        given(screeningRepository.save(screeningMapper.dtoToEntity(screeningRequestDto)))
//                .willReturn(screening);
//
//        assertThat(service.saveScreening(screeningRequestDto, 1L))
//                .isEqualTo(screening);
//    }
//
//    @Test
//    @DisplayName("Should find all Screening by date")
//    void should_find_all_Screening_By_date() {
//        screeningResponseDto = new ScreeningResponseDto(1L, LocalDate.of(2023, 12, 31), LocalTime.of(12, 10), film);
//        secoundScreeningResponseDto = new ScreeningResponseDto(2L, LocalDate.of(2023, 12, 31), LocalTime.of(12, 10), film);
//
//        List<Screening> screeningList = List.of(screening, secoundScreening);
//        List<ScreeningResponseDto> expectedScreeningDtoList = List.of(screeningResponseDto, secoundScreeningResponseDto);
//
//        given(screeningRepository.findScreeningsByDate(LocalDate.of(2023, 12, 31))).willReturn(screeningList);
//        given(screeningMapper.entityToDto(screening)).willReturn(screeningResponseDto);
//        given(screeningMapper.entityToDto(secoundScreening)).willReturn(secoundScreeningResponseDto);
//
//        List<ScreeningResponseDto> actualScreenigDtoList = service.getScreeningsByDate(LocalDate.of(2023, 12, 31));
//        Assertions.assertThat(actualScreenigDtoList)
//                .containsExactlyElementsOf(expectedScreeningDtoList);
//       Assertions.assertThat(expectedScreeningDtoList).isEqualTo(actualScreenigDtoList);
//
//        Mockito.verify(screeningMapper, Mockito.times(1)).entityToDto(screening);
//        Mockito.verify(screeningMapper, Mockito.times(1)).entityToDto(secoundScreening);
//    }





}



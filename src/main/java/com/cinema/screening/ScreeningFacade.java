package com.cinema.screening;

import com.cinema.screening.dto.CreatedScreeningDto;
import com.cinema.screening.dto.ScreeningAvailableSeats;
import com.cinema.screening.dto.ScreeningRequestDto;
import com.cinema.screening.dto.ScreeningResponseDto;

import java.util.List;
import java.time.LocalDate;

public interface ScreeningFacade {
    CreatedScreeningDto saveScreening(ScreeningRequestDto screeningRequestDto, Long filmId);

    List<ScreeningResponseDto> findAllScreenings();

    List<ScreeningResponseDto> getScreeningsByDate(LocalDate date);

    Screening findById(Long screeningId);

    ScreeningAvailableSeats findAvailableSeats(Long screeningId);
}

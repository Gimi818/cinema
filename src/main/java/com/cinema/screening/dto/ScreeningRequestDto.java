package com.cinema.screening.dto;

import java.time.LocalDate;


public record ScreeningRequestDto(LocalDate date, String roomId ) {
}

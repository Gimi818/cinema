package com.cinema.screening.dto;

import java.time.LocalDate;


public record ScreeningResponseDto(Long id , LocalDate date,String roomId) {
}

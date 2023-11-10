package com.cinema.screening;


import com.cinema.screening.dto.ScreeningRequestDto;
import com.cinema.screening.exception.ScreeningNotFoundException;

import com.cinema.screening.exception.ScreeningTooManyInOneDayException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest

class ScreeningValidateTest {
    @Mock
    private ScreeningRepository repository;
    @Mock
    private ScreeningService service;

    @InjectMocks
    private ScreeningValidate screeningValidate;


    @Test
    @DisplayName("Shouldn't throw ScreeningNotFoundException when data is correct")
    void Shouldn_not_throw_ScreeningNotFoundException() {
        // Given
        LocalDate existingDate = LocalDate.of(2024,12,11);

        when(repository.existsByDate(existingDate)).thenReturn(true);

        // Then
        assertDoesNotThrow(() ->
                screeningValidate.checkCorrectData(existingDate)
        );
    }

    @Test
    @DisplayName("Should throw ScreeningNotFoundException")
    void Should_throw_ScreeningNotFoundException() {
        // Given
        LocalDate nonExistingDate = LocalDate.of(2024,12,11);

        when(repository.existsByDate(nonExistingDate)).thenReturn(false);

        // Then
        assertThrows(ScreeningNotFoundException.class, () ->
                screeningValidate.checkCorrectData(nonExistingDate)
        );
    }

    @Test
    @DisplayName("Shouldn't throw ScreeningTooManyInOneDayException when screening in one day < 5")
    void should_not_throw_ScreeningTooManyInOneDayException() {
        // Given
        LocalDate date = LocalDate.of(2025,10,10);
        Screening screening = new Screening(1L,date,LocalTime.of(10,10),null,null);
        List<Screening> screenings = new ArrayList<>();

        screenings.add(screening);

        when(repository.findScreeningsByDate(date)).thenReturn(screenings);

        // Then
        assertDoesNotThrow(() ->
                screeningValidate.checkNumberOfScreeningsDuringDay(new ScreeningRequestDto(date, null))
        );
    }


    @Test
    @DisplayName("Should throw ScreeningTooManyInOneDayException when screening in one day > 5")
    void Should_throw_ScreeningTooManyInOneDayException() {
        // Given
        LocalDate date = LocalDate.of(2025,10,10);
        Screening screening = new Screening(1L,date,null,null,null);
        Screening screening2 = new Screening(2L,date,null,null,null);
        Screening screening3 = new Screening(3L,date,null,null,null);
        Screening screening4 = new Screening(4L,date,null,null,null);
        Screening screening5 = new Screening(5L,date,null,null,null);
        Screening screening6 = new Screening(6L,date,null,null,null);
        List<Screening> screenings = new ArrayList<>();
        screenings.add(screening);
        screenings.add(screening2);
        screenings.add(screening3);
        screenings.add(screening4);
        screenings.add(screening5);
        screenings.add(screening6);

        when(repository.findScreeningsByDate(date)).thenReturn(screenings);

        // Then
        assertThrows(ScreeningTooManyInOneDayException.class, () ->
                screeningValidate.checkNumberOfScreeningsDuringDay(new ScreeningRequestDto(date, null))
        );
    }
}

package com.cinema.screening;

import com.cinema.film.Film;
import com.cinema.screening.dto.ScreeningRequestDto;
import com.cinema.screening.exception.ScreeningNotFoundException;
import com.cinema.screening.exception.ScreeningTimeDifferenceException;
import com.cinema.screening.exception.ScreeningTooLateToCreateNew;
import com.cinema.screening.exception.ScreeningTooManyInOneDayException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.time.LocalDate;

@Component
@AllArgsConstructor
public class ScreeningValidate {

    private final ScreeningRepository repository;

    public void checkCorrectData(LocalDate date) {
        if (!repository.existsByDate(date)) {
            throw new ScreeningNotFoundException(date);
        }
    }

    public void dataValidation(ScreeningRequestDto screeningRequestDto, Film film) {
        checkNumberOfScreeningsDuringDay(screeningRequestDto);
        checkCorrectTime(screeningRequestDto, film);

    }


    public void checkCorrectTime(ScreeningRequestDto newScreening, Film film) {

        List<Screening> screeningsOnSameDay = repository.findScreeningsByDate(newScreening.date());

        if (newScreening.date().isBefore(LocalDate.now())) {
            throw new ScreeningTooLateToCreateNew();
        } else if (newScreening.time().isBefore(LocalTime.now())) {
            throw new ScreeningTooLateToCreateNew();
        }


        for (Screening existingScreening : screeningsOnSameDay) {
            var timeDifference = Duration.between(existingScreening.getTime(), newScreening.time()).toMinutes();
            if (Math.abs(timeDifference) < film.getDurationFilmInMinutes() + 20) {
                throw new ScreeningTimeDifferenceException();
            }
        }
    }


    public void checkNumberOfScreeningsDuringDay(ScreeningRequestDto newScreening) {
        List<Screening> screeningsOnSameDay = repository.findScreeningsByDate(newScreening.date());
        if (screeningsOnSameDay.size() >= 5) {
            throw new ScreeningTooManyInOneDayException();
        }

    }

}









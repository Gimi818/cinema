package com.cinema.screening;

import com.cinema.screening.dto.ScreeningRequestDto;
import com.cinema.screening.exception.ScreeningNotFoundException;
import com.cinema.screening.exception.ScreeningTimeDifferenceException;
import com.cinema.screening.exception.ScreeningTooManyException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
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

    public void dataValidation(ScreeningRequestDto screeningRequestDto) {
        checkNumberOfScreeningsDuringDay(screeningRequestDto);
        checkCorrectTime(screeningRequestDto);

    }


    public void checkCorrectTime(ScreeningRequestDto newScreening) {

        List<Screening> screeningsOnSameDay = repository.findScreeningsByDate(newScreening.date());

        for (Screening existingScreening : screeningsOnSameDay) {
           var timeDifference =  Duration.between(existingScreening.getTime(), newScreening.time()).toHours();
            if (Math.abs(timeDifference) < 4) {
                throw new ScreeningTimeDifferenceException();
            }
        }
    }


    public void checkNumberOfScreeningsDuringDay(ScreeningRequestDto newScreening) {
        List<Screening> screeningsOnSameDay = repository.findScreeningsByDate(newScreening.date());
        if (screeningsOnSameDay.size() >= 3) {
            throw new ScreeningTooManyException();
        }

    }

}









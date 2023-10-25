package com.cinema.ticket;

import com.cinema.screening.Screening;
import com.cinema.ticket.exception.TooLateToBookException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

@Component
@AllArgsConstructor
public class CheckBookingTime {

    public void checkBookingTime(Screening screening) {

        if (screening.getDate().isAfter(LocalDate.now())) {
            return;
        } else if (screening.getDate().isBefore(LocalDate.now())) {
            throw new TooLateToBookException();

        } else if (Duration.between( LocalTime.now(), screening.getTime()).toMinutes() < 15 ) {
            throw new TooLateToBookException();
        }
    }


    }


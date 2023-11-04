package com.cinema.ticket;

import com.cinema.screening.Screening;
import com.cinema.ticket.exception.TooLateToBookException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
@AllArgsConstructor
public class CheckBookingTime {

    public void checkBookingTime(Screening screening) {

        if (screening.getDate().isBefore(LocalDate.now())) {
            throw new TooLateToBookException();
        } else if (screening.getDate().isEqual(LocalDate.now()) && screening.getTime().isBefore(LocalTime.now().plusMinutes(15))) {
            throw new TooLateToBookException();
        }
    }

}


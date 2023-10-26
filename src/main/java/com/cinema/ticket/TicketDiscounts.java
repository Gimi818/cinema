package com.cinema.ticket;

import com.cinema.screening.Screening;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
@AllArgsConstructor
public class TicketDiscounts {


    public int discount(Screening screening) {
        DayOfWeek dayOfWeek = screening.getDate().getDayOfWeek();

        if (dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.WEDNESDAY) {
            return 15;
        } else
            return 25;
    }


}

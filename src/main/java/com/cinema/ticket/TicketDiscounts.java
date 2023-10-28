package com.cinema.ticket;

import com.cinema.screening.Screening;
import com.cinema.ticket.dto.TickedBookingDto;
import com.cinema.ticket.ticketEnum.TicketType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
@AllArgsConstructor
public class TicketDiscounts {


    public int discount(Screening screening) {
        DayOfWeek dayOfWeek = screening.getDate().getDayOfWeek();

        if (dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.TUESDAY) {
            return 15;
        } else
            return 25;
    }


    public int discountForStudents(TickedBookingDto requestDto, Screening screening) {
        if (requestDto.ticketType() == TicketType.REDUCE) {
            return discount(screening) - 5;
        } else
            return discount(screening);
    }
}

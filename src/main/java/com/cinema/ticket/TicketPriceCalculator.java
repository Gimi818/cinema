package com.cinema.ticket;

import com.cinema.api.ExchangeRate;
import com.cinema.api.ExchangeRateRepository;
import com.cinema.screening.Screening;
import com.cinema.ticket.dto.TicketBookingDto;
import com.cinema.ticket.ticketEnum.TicketType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;

import static com.cinema.ticket.TicketPrice.*;

@Component
@AllArgsConstructor

public class TicketPriceCalculator {
    private final ExchangeRateRepository exchangeRateRepository;

    public int eventDiscount(Screening screening) {
        DayOfWeek dayOfWeek = screening.getDate().getDayOfWeek();

        if (dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.TUESDAY) {
            return TICKET_PRICE_WITH_EVENT_REDUCE;
        } else
            return BASIC_TICKET_PRICE;
    }


    public int discountForStudents(TicketBookingDto requestDto, Screening screening) {
        if (requestDto.ticketType() == TicketType.REDUCE) {
            return eventDiscount(screening) - STUDENT_REDUCE;
        } else
            return eventDiscount(screening);
    }

    public BigDecimal finalPrice(TicketBookingDto ticketBookingDto, Screening screening) {

        ExchangeRate exchangeRate = exchangeRateRepository.findByCode(ticketBookingDto.currency().toString());
        double price = (discountForStudents(ticketBookingDto, screening) / exchangeRate.getMid());

        BigDecimal result = BigDecimal.valueOf(price);
        return result.setScale(1, RoundingMode.HALF_UP);
    }

}

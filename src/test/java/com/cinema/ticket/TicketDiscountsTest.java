package com.cinema.ticket;

import com.cinema.api.ExchangeRateRepository;
import com.cinema.screening.Screening;
import com.cinema.ticket.dto.TicketBookingDto;
import com.cinema.ticket.priceCalculator.TicketPriceCalculator;
import com.cinema.ticket.ticketEnum.Currency;
import com.cinema.ticket.ticketEnum.TicketType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TicketDiscountsTest {
    @Mock
    private ExchangeRateRepository repository;
    @Test
    @DisplayName("Should return 20 ticket price with event discount")
    void should_return_price_with_discount() {
        // Given
        TicketPriceCalculator ticketDiscounts = new TicketPriceCalculator(repository);
        Screening screening = new Screening( LocalDate.of(2023, 11, 3), LocalTime.of(12, 11), null, null);

        // When
        int result = ticketDiscounts.eventDiscount(screening);

        // Then
        assertEquals(20, result);
    }

    @Test
    @DisplayName("Should return 28 ticket  normal price without discount")
    void should_return_basic_price() {
        // Given
        TicketPriceCalculator ticketDiscounts = new TicketPriceCalculator(repository);
        Screening screening = new Screening( LocalDate.of(2023, 11, 5), LocalTime.of(12, 11), null, null);

        // When
        int result = ticketDiscounts.eventDiscount(screening);

        // Then
        assertEquals(28, result);
    }

    @Test
    @DisplayName("Should return 15 ticket price with event and student discount")
    void should_return_15_price() {
        // Given
        TicketPriceCalculator ticketDiscounts = new TicketPriceCalculator(repository);
        TicketBookingDto ticketBookingDto = new TicketBookingDto(TicketType.REDUCE,Currency.PLN,1,2);
        Screening screening = new Screening( LocalDate.of(2023, 11, 3), LocalTime.of(12, 11), null, null);

        // When
        int result = ticketDiscounts.discountForStudents(ticketBookingDto, screening);

        // Then
        assertEquals(15, result);
    }

}

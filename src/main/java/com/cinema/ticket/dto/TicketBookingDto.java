package com.cinema.ticket.dto;

import com.cinema.ticket.ticketEnum.Currency;
import com.cinema.ticket.ticketEnum.TicketType;

public record TicketBookingDto(TicketType ticketType, Currency currency, int rowsNumber,
                               int seatInRow) {
}

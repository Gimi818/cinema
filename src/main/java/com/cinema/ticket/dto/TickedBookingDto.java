package com.cinema.ticket.dto;

import com.cinema.ticket.ticketEnum.TicketType;

public record TickedBookingDto(TicketType ticketType,   int rowsNumber,
                               int seatInRow) {
}

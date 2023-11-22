package com.cinema.ticket.dto;

import com.cinema.ticket.ticketEnum.TicketStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public record TicketBookedDto(String filmTitle, LocalDate screeningDate,
                              LocalTime screeningTime, int rowsNumber,
                              int seatInRow,TicketStatus status) {
}

package com.cinema.ticket.dto;

import com.cinema.ticket.TicketStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public record TicketResponseDto(TicketStatus status,

                                String filmTitle,
                                LocalDate screeningDate,
                                LocalTime screeningTime,
                                int ticketPrice
) {
}

package com.cinema.ticket.dto;

import com.cinema.ticket.TicketStatus;
import com.cinema.ticket.TicketType;

import java.time.LocalDate;
import java.time.LocalTime;

public record TicketResponseDto(TicketStatus status,

                                String filmTitle,
                                LocalDate screeningDate,
                                LocalTime screeningTime,
                                String name,
                                int ticketPrice,
                                TicketType ticketType
) {
}

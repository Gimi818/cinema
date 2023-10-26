package com.cinema.ticket;

import com.cinema.screening.Screening;
import com.cinema.screening.ScreeningRepository;
import com.cinema.screening.exception.ScreeningNotFoundByIdException;
import com.cinema.ticket.exception.TicketNotFoundException;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Log4j2
public class TicketService {

    private final TicketRepository ticketRepository;
    private final ScreeningRepository screeningRepository;
    private final CheckBookingTime checkBookingTime;
    private final TicketDiscounts ticketDiscounts;

    @Transactional
    public Ticket bookTicket(Long screeningId) {
        Screening screening = screeningRepository.findById(screeningId).orElseThrow(() -> new ScreeningNotFoundByIdException(screeningId));
        checkBookingTime.checkBookingTime(screening);
        Ticket newTicket = Ticket.builder()
                .filmTitle(screening.getFilm().getTitle())
                .screeningDate(screening.getDate())
                .screeningTime(screening.getTime())
                .status(TicketStatus.ACTIVE)
                .TicketPrice(ticketDiscounts.discount(screening))
                .build();
        ticketRepository.save(newTicket);
        return newTicket;

    }


    public void cancelTicket(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new TicketNotFoundException(ticketId));
        ticket.setStatus(TicketStatus.CANCELLED);
        ticketRepository.delete(ticket);
    }


}

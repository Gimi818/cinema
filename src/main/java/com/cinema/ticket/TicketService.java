package com.cinema.ticket;

import com.cinema.screening.Screening;
import com.cinema.screening.ScreeningRepository;
import com.cinema.screening.exception.ScreeningNotFoundByIdException;
import com.cinema.ticket.exception.TicketNotFoundException;
import com.cinema.ticket.exception.TooLateToBookException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

@Service
@AllArgsConstructor
@Log4j2
public class TicketService {

    private final TicketRepository ticketRepository;
    private final ScreeningRepository screeningRepository;

    @Transactional
    public Ticket bookTicket(Long screeningId) {
        Screening screening = screeningRepository.findById(screeningId).orElseThrow(() -> new ScreeningNotFoundByIdException(screeningId));
        checkBookingTime(screening);
        Ticket newTicket = Ticket.builder()
                .filmTitle(screening.getFilm().getTitle())
                .screeningDate(screening.getDate())
                .screeningTime(screening.getTime())
                .cinemaRoomId(screening.getRoomId())
                .status(TicketStatus.ACTIVE)
                .build();
        ticketRepository.save(newTicket);
        return newTicket;

    }

    public void checkBookingTime(Screening screening) {
        if (screening.getDate().isAfter(LocalDate.now())) {
           return;
        } else if (Duration.between(screening.getTime(), LocalTime.now()).toMinutes() > 30) {
            throw new TooLateToBookException();
        }
    }

    public void cancelTicket(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new TicketNotFoundException(ticketId));
        ticket.setStatus(TicketStatus.CANCELLED);
        ticketRepository.delete(ticket);
    }


}

package com.cinema.ticket;

import com.cinema.emailSender.EmailWithPDF;
import com.cinema.screening.Screening;
import com.cinema.screening.ScreeningRepository;
import com.cinema.screening.exception.ScreeningNotFoundByIdException;
import com.cinema.ticket.dto.TickedRequestDto;
import com.cinema.ticket.exception.TicketNotFoundException;


import com.cinema.user.User;
import com.cinema.user.UserRepository;
import jakarta.mail.MessagingException;
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
    private final UserRepository userRepository;
    private final EmailWithPDF email;

    @Transactional
    public Ticket bookTicket(Long screeningId, Long userId, TickedRequestDto requestDto) throws MessagingException {
        Screening screening = screeningRepository.findById(screeningId).orElseThrow(() -> new ScreeningNotFoundByIdException(screeningId));
        User user = userRepository.findById(userId).orElseThrow(() -> new ScreeningNotFoundByIdException(screeningId));

        checkBookingTime.checkBookingTime(screening);
        Ticket newTicket = Ticket.builder()
                .filmTitle(screening.getFilm().getTitle())
                .screeningDate(screening.getDate())
                .screeningTime(screening.getTime())
                .name(user.getFirstName() + " " + user.getLastName())
                .status(TicketStatus.ACTIVE)
                .ticketType(requestDto.ticketType())
                .TicketPrice(ticketDiscounts.discountForStudents(requestDto,screening))
                .build();
        ticketRepository.save(newTicket);
        email.sendEmailWithPDF(user.getEmail(), newTicket);
        return newTicket;

    }


    public void cancelTicket(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new TicketNotFoundException(ticketId));
        ticket.setStatus(TicketStatus.CANCELLED);
        ticketRepository.delete(ticket);
    }


}

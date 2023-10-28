package com.cinema.ticket;

import com.cinema.emailSender.EmailWithPDF;
import com.cinema.screening.Screening;
import com.cinema.screening.ScreeningRepository;
import com.cinema.screening.exception.ScreeningNotFoundByIdException;
import com.cinema.seats.SeatCheckAvailability;
import com.cinema.ticket.dto.TickedBookingDto;
import com.cinema.ticket.exception.TicketNotFoundException;


import com.cinema.ticket.ticketEnum.TicketStatus;
import com.cinema.user.User;
import com.cinema.user.UserRepository;
import com.cinema.user.exception.UserNotFoundByIdException;
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
    private final SeatCheckAvailability seatCheckAvailability;


    @Transactional
    public Ticket bookTicket(Long screeningId, Long userId, TickedBookingDto tickedDto) throws MessagingException {
        Screening screening = getScreeningById(screeningId);
        User user = getUserById(userId);

        checkBookingTime.checkBookingTime(screening);
        Ticket newTicket = createNewTicket(screening, user, tickedDto);
        ticketRepository.save(newTicket);
        email.sendEmailWithPDF(user.getEmail(), newTicket);
        seatCheckAvailability.checkSeat(screeningId, tickedDto.rowsNumber(), tickedDto.seatInRow());

        return newTicket;
    }

    private Ticket createNewTicket(Screening screening, User user, TickedBookingDto tickedDto) {

        return Ticket.builder()
                .filmTitle(screening.getFilm().getTitle())
                .screeningDate(screening.getDate())
                .screeningTime(screening.getTime())
                .name(concatenateUserName(user.getFirstName(), user.getLastName()))
                .status(TicketStatus.ACTIVE)
                .ticketType(tickedDto.ticketType())
                .ticketPrice(ticketDiscounts.discountForStudents(tickedDto, screening))
                .rowsNumber(tickedDto.rowsNumber())
                .seatInRow(tickedDto.seatInRow())
                .userId(user.getId())
                .build();
    }

    private Screening getScreeningById(Long screeningId) {
        return screeningRepository.findById(screeningId)
                .orElseThrow(() -> new ScreeningNotFoundByIdException(screeningId));
    }

    private String concatenateUserName(String firstName, String lastName) {
        return firstName + " " + lastName;
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundByIdException(userId));
    }


    public void cancelTicket(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new TicketNotFoundException(ticketId));
        ticket.setStatus(TicketStatus.CANCELLED);
        ticketRepository.delete(ticket);
    }


}

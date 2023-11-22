package com.cinema.ticket;

import com.cinema.common.exception.exceptions.NotFoundException;
import com.cinema.common.exception.exceptions.TooLateException;
import com.cinema.emailSender.EmailWithPDF;
import com.cinema.screening.Screening;

import com.cinema.screening.ScreeningService;
import com.cinema.seats.SeatService;
import com.cinema.ticket.dto.TicketBookedDto;
import com.cinema.ticket.dto.TicketBookingDto;

import com.cinema.ticket.priceCalculator.TicketPriceCalculator;
import com.cinema.ticket.ticketEnum.TicketStatus;

import static com.cinema.ticket.TicketService.ErrorMessages.*;

import com.cinema.user.User;

import com.cinema.user.UserService;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@AllArgsConstructor
@Log4j2
public class TicketService {

    private final TicketRepository ticketRepository;

    private final TicketPriceCalculator ticketPrice;
    private final ScreeningService screeningService;
    private final UserService userService;
    private final EmailWithPDF email;
    private final SeatService seatService;
    private final TicketMapper mapper;


    @Transactional
    public TicketBookedDto bookTicket(Long screeningId, Long userId, TicketBookingDto tickedDto) throws MessagingException {
        Screening screening = screeningService.findById(screeningId);
        User user = userService.findById(userId);
        checkBookingTime(screening);

        Ticket newTicket = createNewTicket(screening, user, tickedDto);
        seatService.checkSeatsAvailability(screeningId, tickedDto.rowsNumber(), tickedDto.seatInRow());

        ticketRepository.save(newTicket);
        email.sendEmailWithPDF(user.getEmail(), newTicket);
        return mapper.bookedTicketToDto(newTicket);
    }


    public Ticket createNewTicket(Screening screening, User user, TicketBookingDto tickedDto) {

        return Ticket.builder()
                .filmTitle(screening.getFilm().getTitle())
                .screeningDate(screening.getDate())
                .screeningTime(screening.getTime())
                .name(concatenateUserName(user.getFirstName(), user.getLastName()))
                .status(TicketStatus.ACTIVE)
                .ticketType(tickedDto.ticketType())
                .ticketPrice(ticketPrice.finalPrice(tickedDto, screening))
                .rowsNumber(tickedDto.rowsNumber())
                .roomNumber(1)
                .currency(tickedDto.currency())
                .seatInRow(tickedDto.seatInRow())
                .userId(user.getId())
                .build();
    }

    @Transactional
    public void cancelTicket(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new NotFoundException(NOT_FOUND_BY_ID, ticketId));
        ticket.setStatus(TicketStatus.CANCELLED);
        ticketRepository.delete(ticket);
    }

    public void checkBookingTime(Screening screening) {

        if (screening.getDate().isBefore(LocalDate.now())) {
            throw new TooLateException(TOO_LATE_TO_BOOK);
        } else if (screening.getDate().isEqual(LocalDate.now()) && screening.getTime().isBefore(LocalTime.now().plusMinutes(15))) {
            throw new TooLateException(TOO_LATE_TO_BOOK);
        }
    }

    public String concatenateUserName(String firstName, String lastName) {
        return firstName + " " + lastName;
    }

    static final class ErrorMessages {
        static final String NOT_FOUND_BY_ID = "Ticket with id %d not found";
        static final String TOO_LATE_TO_BOOK = "Too late to book a ticket";

    }

}

package com.cinema.ticket;

import com.cinema.common.exception.exceptions.NotFoundException;
import com.cinema.emailSender.EmailWithPDF;
import com.cinema.film.Film;
import com.cinema.film.filmCategory.FilmCategory;
import com.cinema.screening.Screening;
import com.cinema.screening.ScreeningFacade;

import com.cinema.seats.Seat;
import com.cinema.seats.SeatFacade;
import com.cinema.ticket.dto.TicketBookedDto;
import com.cinema.ticket.dto.TicketBookingDto;
import com.cinema.ticket.dto.TicketResponseDto;
import com.cinema.ticket.priceCalculator.TicketPriceCalculator;
import com.cinema.ticket.ticketEnum.Currency;
import com.cinema.ticket.ticketEnum.TicketStatus;
import com.cinema.ticket.ticketEnum.TicketType;
import com.cinema.user.UserFacade;
import com.cinema.user.userEnum.AccountType;
import com.cinema.user.User;
import com.cinema.user.userEnum.UserRole;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class TicketServiceTest {
    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private UserFacade userFacade;
    @Mock
    private SeatFacade seatFacade;

    @Mock
    private Film film;

    @Mock
    private TicketPriceCalculator ticketPriceCalculator;
    @Mock
    private TicketBookingDto ticketBookingDto;
    @Mock
    private EmailWithPDF email;

    @Mock
    private ScreeningFacade screeningFacade;

    @InjectMocks
    private TicketService ticketService;
    @Mock
    private User user;
    @Mock
    private Screening screening;
    @Mock
    private TicketPriceCalculator ticketDiscounts;
    @Mock
    TicketMapper mapper;

    @Test
    @DisplayName("Should create new ticket")
    public void should_create_new_ticket() {

        LocalDate date = LocalDate.of(2034, 10, 10);
        LocalTime time = LocalTime.of(10, 10);

        Film film1 = new Film("TOP GUN", FilmCategory.ACTION, 120);
        Screening screening1 = new Screening(date, time, film1, null);


        User user = new User("Adam", "New", "aa@.cc", "asdawa", UserRole.ADMIN, AccountType.UNCONFIRMED, "token");
        TicketBookingDto ticketBookingDto1 = new TicketBookingDto(TicketType.NORMAL, Currency.USD, 1, 1);

        when(ticketPriceCalculator.finalPrice(ticketBookingDto1, screening1)).thenReturn(BigDecimal.valueOf(10));

        Ticket ticket = ticketService.createNewTicket(screening1, user, ticketBookingDto1);

        assertEquals("TOP GUN", ticket.getFilmTitle());
        assertEquals(date, ticket.getScreeningDate());
        assertEquals(time, ticket.getScreeningTime());
        assertEquals("Adam New", ticket.getName());
        assertEquals(TicketStatus.ACTIVE, ticket.getStatus());
        assertEquals(TicketType.NORMAL, ticket.getTicketType());
        assertEquals(1, ticket.getRowsNumber());
        assertEquals(1, ticket.getRoomNumber());
        assertEquals(Currency.USD, ticket.getCurrency());
        assertEquals(1, ticket.getSeatInRow());
    }

    @Test
    @DisplayName("Should throw TicketNotFoundException")
    void should_throw_TicketNotFoundException() {
        // Given
        Long nonExistingTicketId = 5L;

        when(ticketRepository.findById(nonExistingTicketId)).thenReturn(java.util.Optional.empty());

        // Then
        assertThrows(NotFoundException.class, () ->
                ticketService.cancelTicket(nonExistingTicketId)
        );
    }


}

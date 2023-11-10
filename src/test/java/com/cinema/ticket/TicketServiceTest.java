package com.cinema.ticket;

import com.cinema.emailSender.EmailWithPDF;
import com.cinema.film.Film;
import com.cinema.film.filmCategory.FilmCategory;
import com.cinema.screening.Screening;
import com.cinema.screening.ScreeningRepository;
import com.cinema.screening.ScreeningService;
import com.cinema.seats.SeatService;
import com.cinema.ticket.dto.TicketBookingDto;
import com.cinema.ticket.exception.TicketNotFoundException;
import com.cinema.ticket.ticketEnum.Currency;
import com.cinema.ticket.ticketEnum.TicketStatus;
import com.cinema.ticket.ticketEnum.TicketType;
import com.cinema.user.userEnum.AccountType;
import com.cinema.user.User;
import com.cinema.user.UserRepository;
import com.cinema.user.userEnum.UserRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class TicketServiceTest {
    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private ScreeningRepository screeningRepository;
    @Mock
    private CheckBookingTime checkBookingTime;
    @Mock
    private Film film;
    @Mock
    private SeatService seatService;
    @Mock
    private TicketPriceCalculator ticketPriceCalculator;
    @Mock
    private TicketBookingDto ticketBookingDto;
    @Mock
    private EmailWithPDF email;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ScreeningService screeningService;
    @InjectMocks
    private TicketService ticketService;
    @Mock
    private User user;
    @Mock
    private Screening screening;
    @Mock
    private TicketPriceCalculator ticketDiscounts;

    @Test
    @DisplayName("Should create new ticket")
    public void should_create_new_ticket() {

        LocalDate date = LocalDate.of(2034, 10, 10);
        LocalTime time = LocalTime.of(10, 10);

        Film film1 = new Film(1L, "TOP GUN", FilmCategory.ACTION, 120);
        Screening screening1 = new Screening(1L, date, time, film1, null);
        screeningRepository.save(screening1);

        User user = new User(1L, "Adam", "New", "aa@.cc", "asdawa", UserRole.ADMIN, AccountType.UNCONFIRMED, "token");
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
        assertEquals(Long.valueOf(1), ticket.getUserId());
    }

//    @Test
//    public void Should_booking_ticket() throws MessagingException {
//
//
//        LocalDate date = LocalDate.of(2034, 10, 10);
//        LocalTime time = LocalTime.of(10, 10);
//
//        TicketBookingDto ticketBookingDto1 = new TicketBookingDto(TicketType.NORMAL, Currency.USD, 1, 1);
//        User user = new User(1L, "Adam", "New", "aa@.cc", "asdawa", UserRole.ADMIN, AccountType.UNCONFIRMED, "token");
//        Film film1 = new Film(1L, "TOP GUN", FilmCategory.ACTION, 120);
//        Screening screening1 = new Screening(1L, date, time, film1, null);
//
//        Ticket newTicket = new Ticket(1L, ticketService.concatenateUserName(user.getFirstName(), user.getLastName()), "TOP GUN", date, time, BigDecimal.valueOf(10.0), 1, 1, 1, 1L, TicketStatus.ACTIVE, TicketType.NORMAL, Currency.USD);
//        when(ticketService.createNewTicket(screening1, user, ticketBookingDto1)).thenReturn(newTicket);
//
//
//        Ticket ticket = ticketService.bookTicket(1L, 1L, ticketBookingDto1);
//
//        assertEquals("TOP GUN", ticket.getFilmTitle());
//        assertEquals(date, ticket.getScreeningDate());
//        assertEquals(time, ticket.getScreeningTime());
//        assertEquals("Adam New", ticket.getName());
//        assertEquals(TicketStatus.ACTIVE, ticket.getStatus());
//        assertEquals(TicketType.NORMAL, ticket.getTicketType());
//        assertEquals(BigDecimal.valueOf(10), ticket.getTicketPrice());
//        assertEquals(1, ticket.getRowsNumber());
//        assertEquals(1, ticket.getRoomNumber());
//        assertEquals(Currency.USD, ticket.getCurrency());
//        assertEquals(1, ticket.getSeatInRow());
//        assertEquals(Long.valueOf(1), ticket.getUserId());
//
//        Mockito.verify(ticketRepository, times(1)).save(newTicket);
//    }


    @Test
    @DisplayName("Should throw TicketNotFoundException")
    void should_throw_TicketNotFoundException() {
        // Given
        Long nonExistingTicketId = 5L;

        when(ticketRepository.findById(nonExistingTicketId)).thenReturn(java.util.Optional.empty());

        // Then
        assertThrows(TicketNotFoundException.class, () ->
                ticketService.cancelTicket(nonExistingTicketId)
        );
    }


}

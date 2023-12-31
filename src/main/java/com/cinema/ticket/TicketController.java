package com.cinema.ticket;

import com.cinema.ticket.dto.TicketBookedDto;
import com.cinema.ticket.dto.TicketBookingDto;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.cinema.ticket.TicketController.Routes.*;

@RestController
@RequiredArgsConstructor
class TicketController {

    private final TicketService service;

    @PostMapping(BOOKING)
    public ResponseEntity<TicketBookedDto> booking(@PathVariable UUID userUuid,
                                                   @PathVariable Long screeningId,
                                                   @RequestBody TicketBookingDto ticketRequestDto) throws MessagingException {
        return new ResponseEntity<>(service.bookTicket(screeningId, userUuid, ticketRequestDto), HttpStatus.CREATED);
    }

    static final class Routes {
        static final String ROOT = "/book";
        static final String BOOKING = ROOT + "/{userUuid}/{screeningId}";


    }
}


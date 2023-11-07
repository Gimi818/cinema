package com.cinema.ticket;

import com.cinema.ticket.dto.TicketBookingDto;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
public class TicketController {

    private final TicketService service;

    @PostMapping("/{userId}/{screeningId}")
    public ResponseEntity<Ticket> booking(@PathVariable Long userId,
                                          @PathVariable Long screeningId,
                                          @RequestBody TicketBookingDto tickedRequestDto) throws MessagingException {
        return new ResponseEntity<>(service.bookTicket(screeningId, userId, tickedRequestDto), HttpStatus.CREATED);
    }
}

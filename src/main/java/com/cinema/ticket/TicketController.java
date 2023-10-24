package com.cinema.ticket;

import com.cinema.screening.Screening;
import com.cinema.screening.dto.ScreeningRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
public class TicketController {


private final TicketService service;


    @PostMapping("/{screeningId}")
    public ResponseEntity<Ticket> saveScreening(  @PathVariable Long screeningId) {
        return new ResponseEntity<>(service.bookTicket(screeningId), HttpStatus.CREATED);
    }
}

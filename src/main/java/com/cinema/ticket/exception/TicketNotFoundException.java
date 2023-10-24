package com.cinema.ticket.exception;

public class TicketNotFoundException extends RuntimeException{

    public TicketNotFoundException(Long ticketId) {
        super(String.format("Ticket with id %d not found", ticketId));

    }
}

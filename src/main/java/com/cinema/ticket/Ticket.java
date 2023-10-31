package com.cinema.ticket;

import com.cinema.ticket.ticketEnum.TicketStatus;
import com.cinema.ticket.ticketEnum.TicketType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "ticket")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String filmTitle;
    private LocalDate screeningDate;
    private LocalTime screeningTime;
    private int ticketPrice;
    private int rowsNumber;
    private int seatInRow;
    private int roomNumber;
    private Long userId;
    @Enumerated(EnumType.STRING)
    private TicketStatus status;
    @Enumerated(value = EnumType.STRING)
    private TicketType ticketType;

}

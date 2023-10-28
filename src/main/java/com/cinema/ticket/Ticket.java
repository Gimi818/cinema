package com.cinema.ticket;

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
    @Enumerated(EnumType.STRING)
    private TicketStatus status;
    @Enumerated(value = EnumType.STRING)
    private TicketType ticketType;
    String name;
    private String filmTitle;

    private LocalDate screeningDate;
    private LocalTime screeningTime;

    private int ticketPrice;


  //  private int rowNumber;

   // private int seatNumber;

  //  private Long userId;
}

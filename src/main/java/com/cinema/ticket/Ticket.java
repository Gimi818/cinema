package com.cinema.ticket;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    private String filmTitle;

    private LocalDate screeningDate;
    private LocalTime screeningTime;

    private String cinemaRoomId;

  //  private int rowNumber;

   // private int seatNumber;

  //  private Long userId;
}

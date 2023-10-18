package com.cinema.cinemaRoom.seats;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "seats")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int rowNumber;

    private int number;

    @Enumerated(value = EnumType.STRING)
    private SeatStatus status;
}

package com.cinema.seats;

import com.cinema.screening.Screening;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "seats")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int rowsNumber;

    private int seatInRow;
    @ManyToOne
    @JoinColumn(name = "screening_id")
    private Screening screening;
    @Enumerated(value = EnumType.STRING)
    private SeatStatus status;
}
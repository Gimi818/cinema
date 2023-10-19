package com.cinema.screening;

import com.cinema.cinemaRoom.seats.Seat;
import com.cinema.film.Film;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "screening")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime date;

    @ManyToOne
    private Film film;

    private String roomId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "screening_id")
    private List<Seat> seats;
}

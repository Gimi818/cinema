package com.cinema.screening;


import com.cinema.seats.Seat;
import com.cinema.film.Film;
import com.cinema.common.entity.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "screening")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Screening  extends AbstractEntity {

    private LocalDate date;

    private LocalTime time;

    @ManyToOne
    private Film film;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "screening_id")
    private List<Seat> seats;
}

package com.cinema.screening;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "screening")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private LocalTime time;
//    @ManyToOne
//    private Film film;

    private String roomId;
//
//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "screening_id")
//    private List<Seat> seats;
}

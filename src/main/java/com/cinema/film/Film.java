package com.cinema.film;

import com.cinema.film.filmCategory.FilmCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "film")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @Enumerated(EnumType.STRING)
    private FilmCategory category;

    private int durationFilmInMinutes;
}

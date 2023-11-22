package com.cinema.film;

import com.cinema.film.filmCategory.FilmCategory;

import com.cinema.common.entity.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "film")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Film  extends AbstractEntity {
    private String title;
    @Enumerated(EnumType.STRING)
    private FilmCategory category;
    private int durationFilmInMinutes;
}

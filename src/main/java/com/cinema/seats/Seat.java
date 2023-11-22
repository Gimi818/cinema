package com.cinema.seats;

import com.cinema.screening.Screening;
import com.cinema.common.entity.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "seats")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Seat  extends  AbstractEntity{

    private int rowsNumber;

    private int seatInRow;
    @ManyToOne
    @JoinColumn(name = "screening_id")
    @JsonIgnore
    private Screening screening;
    @Enumerated(value = EnumType.STRING)
    private SeatStatus status;


}

package com.cinema.seats;

import com.cinema.screening.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    Seat findByScreeningAndRowsNumberAndSeatInRow(Screening screening, int rowsNumber, int seatInRow);

}

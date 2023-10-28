package com.cinema.screening;

import com.cinema.seats.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Long> {

    @Query("SELECT s FROM Screening s WHERE s.date = :date")
    List<Screening> findScreeningsByDate(@Param("date") LocalDate date);
    @Query("SELECT s FROM Screening s LEFT JOIN FETCH s.seats WHERE s.id = :id")
    Screening findScreeningByIdWithSeats(@Param("id") Long id);

    boolean existsByDate(LocalDate date);

}

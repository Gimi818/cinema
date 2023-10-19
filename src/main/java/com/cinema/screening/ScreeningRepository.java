package com.cinema.screening;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ScreeningRepository extends JpaRepository<Screening, Long> {


    @Query("SELECT s FROM Screening s WHERE s.date = :date")
    List<Screening> getScreeningsByDate(LocalDateTime date);
}

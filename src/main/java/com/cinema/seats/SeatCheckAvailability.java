package com.cinema.seats;

import com.cinema.screening.Screening;
import com.cinema.screening.ScreeningRepository;
import com.cinema.screening.exception.ScreeningNotFoundByIdException;
import com.cinema.seats.exception.SeatAlreadyTakenException;
import com.cinema.seats.exception.SeatNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SeatCheckAvailability {
    private final ScreeningRepository screeningRepository;
    private final SeatRepository seatRepository;
  //  private final ScreeningRepository screeningRepository;

    public void checkSeat(Long screeningId, int rowsNumber, int seatInRow) {
        Screening screening = getScreeningById(screeningId);
        Seat seat = seatRepository.findByScreeningAndRowsNumberAndSeatInRow(screening, rowsNumber, seatInRow);

        if (seat != null) {
            if (seat.getStatus() == SeatStatus.AVAILABLE) {
                seat.setStatus(SeatStatus.TAKEN);
                seatRepository.save(seat);
            } else if (seat.getStatus() == SeatStatus.TAKEN) {
                throw new SeatAlreadyTakenException(rowsNumber, seatInRow);
            }
        } else {
            throw new SeatNotFoundException(rowsNumber, seatInRow);
        }


    }

    private Screening getScreeningById(Long screeningId) {
        return screeningRepository.findById(screeningId)
                .orElseThrow(() -> new ScreeningNotFoundByIdException(screeningId));
    }
}

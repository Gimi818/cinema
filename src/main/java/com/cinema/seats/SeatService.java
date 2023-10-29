package com.cinema.seats;

import com.cinema.screening.Screening;
import com.cinema.screening.ScreeningRepository;
import com.cinema.screening.exception.ScreeningNotFoundByIdException;
import com.cinema.seats.exception.SeatAlreadyTakenException;
import com.cinema.seats.exception.SeatNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Log4j2

public class SeatService {
    private final ScreeningRepository screeningRepository;
    private final SeatRepository seatRepository;

    public void checkSeatsAvailability(Long screeningId, int rowsNumber, int seatInRow) {
        Screening screening = getScreeningById(screeningId);

        Seat seat = seatRepository.findByScreeningAndRowsNumberAndSeatInRow(screening, rowsNumber, seatInRow);

        if (seat != null) {
            if (seat.getStatus() == SeatStatus.AVAILABLE) {
                seat.setStatus(SeatStatus.TAKEN);
                seatRepository.save(seat);
                log.info("Seat has been reserved");

            } else if (seat.getStatus() == SeatStatus.TAKEN) {
                throw new SeatAlreadyTakenException();
            }
        } else {
            throw new SeatNotFoundException(rowsNumber, seatInRow);
        }


    }

    private Screening getScreeningById(Long screeningId) {
        return screeningRepository.findById(screeningId)
                .orElseThrow(() -> new ScreeningNotFoundByIdException(screeningId));
    }
    public  Seat createSeat(int rowNumber, int seatInRow, SeatStatus status) {
        Seat seat = new Seat();
        seat.setRowsNumber(rowNumber);
        seat.setSeatInRow(seatInRow);
        seat.setStatus(status);
        return seat;
    }


}

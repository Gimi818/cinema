package com.cinema.seats;

import com.cinema.common.exception.exceptions.AlreadyTakenException;
import com.cinema.common.exception.exceptions.NotFoundException;
import com.cinema.screening.Screening;
import com.cinema.screening.ScreeningFacade;


import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Service;

import static com.cinema.seats.SeatService.ErrorMessages.*;

@Service
@AllArgsConstructor
@Log4j2
class SeatService implements SeatFacade {

    private final ScreeningFacade screeningFacade;
    private final SeatRepository seatRepository;


    public void checkSeatsAvailability(Long screeningId, int rowsNumber, int seatInRow) {
        Screening screening = screeningFacade.findById(screeningId);

        Seat seat = seatRepository.findByScreeningAndRowsNumberAndSeatInRow(screening, rowsNumber, seatInRow);

        if (seat != null) {
            if (seat.getStatus() == SeatStatus.AVAILABLE) {
                seat.setStatus(SeatStatus.TAKEN);
                seatRepository.save(seat);

            } else if (seat.getStatus() == SeatStatus.TAKEN) {
                throw new AlreadyTakenException(SEAT_ALREADY_TAKEN);
            }
        } else {
            throw new NotFoundException(NOT_FOUND_SEAT, rowsNumber, seatInRow);
        }

    }

    public Seat createSeat(int rowNumber, int seatInRow, SeatStatus status) {
        Seat seat = new Seat();
        seat.setRowsNumber(rowNumber);
        seat.setSeatInRow(seatInRow);
        seat.setStatus(status);
        return seat;
    }

    static final class ErrorMessages {
        static final String NOT_FOUND_SEAT = "Seat not found for provided row %d and seat number %d.";
        static final String NOT_FOUND_BY_ID = "Screening with id %d not found";
        static final String SEAT_ALREADY_TAKEN = "This seat is already taken.";


    }


}

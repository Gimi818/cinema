package com.cinema.seats;

import com.cinema.screening.Screening;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SeatService {


    public  Seat createSeat(int rowNumber, int seatInRow, SeatStatus status) {
        Seat seat = new Seat();
        seat.setRowsNumber(rowNumber);
        seat.setSeatInRow(seatInRow);
        seat.setStatus(status);
        return seat;
    }


}

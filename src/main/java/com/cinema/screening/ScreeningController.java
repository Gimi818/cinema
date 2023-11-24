package com.cinema.screening;

import com.cinema.screening.dto.CreatedScreeningDto;
import com.cinema.screening.dto.ScreeningAvailableSeats;
import com.cinema.screening.dto.ScreeningRequestDto;
import com.cinema.screening.dto.ScreeningResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
class ScreeningController {

    private final ScreeningFacade screeningFacade;

    @GetMapping(Routes.ROOT)
    public ResponseEntity<List<ScreeningResponseDto>> getScreeningsByDate(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<ScreeningResponseDto> screenings = screeningFacade.getScreeningsByDate(date);
        return ResponseEntity.status(HttpStatus.OK).body(screenings);
    }

    @PostMapping(Routes.SAVE)
    public ResponseEntity<CreatedScreeningDto> saveScreening(@RequestBody ScreeningRequestDto screeningDto, @PathVariable Long filmId) {
        return new ResponseEntity<>(screeningFacade.saveScreening(screeningDto, filmId), HttpStatus.CREATED);
    }

    @GetMapping(Routes.FIND_AVAILABLE_SEATS)
    public ResponseEntity<ScreeningAvailableSeats> findAvailableSeats(@PathVariable Long id) {
        ScreeningAvailableSeats screeningAvailableSeats = screeningFacade.findAvailableSeats(id);
        return ResponseEntity.status(HttpStatus.OK).body(screeningAvailableSeats);
    }

    @GetMapping(Routes.FIND_ALL)
    public ResponseEntity<List<ScreeningResponseDto>> findAll() {
        List<ScreeningResponseDto> allScreening = screeningFacade.findAllScreenings();
        return ResponseEntity.status(HttpStatus.OK).body(allScreening);
    }

    static final class Routes {
        static final String ROOT = "/screenings";
        static final String SAVE = ROOT + "/{filmId}";
        static final String FIND_ALL = ROOT + "/all";
        static final String FIND_AVAILABLE_SEATS = ROOT + "/{id}";

    }
}


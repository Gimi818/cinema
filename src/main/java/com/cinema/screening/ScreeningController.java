package com.cinema.screening;

import com.cinema.screening.dto.ScreeningRequestDto;
import com.cinema.screening.dto.ScreeningResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/screenings")
public class ScreeningController {

    private final ScreeningService service;

    @PostMapping("/add")
    public ResponseEntity<Screening> saveScreening(@RequestBody ScreeningRequestDto screeningDto) {
        return new ResponseEntity<>(service.saveScreening(screeningDto), HttpStatus.CREATED);
    }

    @GetMapping("/screenings/{date}")
    public List<Screening> getScreeningsByDateAfter(@PathVariable LocalDateTime date) {
        ScreeningResponseDto screeningResponseDto = service.getScreeningsByDate(date);
        return ResponseEntity.status(HttpStatus.OK).body(screeningResponseDto);
    }


}

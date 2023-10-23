package com.cinema.screening;

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
@RequestMapping("/screenings")
public class ScreeningController {

    private final ScreeningService service;

    @PostMapping("/add")
    public ResponseEntity<Screening> saveScreening(@RequestBody ScreeningRequestDto screeningDto) {
        return new ResponseEntity<>(service.saveScreening(screeningDto), HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<ScreeningResponseDto>> getScreeningsByDate(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<ScreeningResponseDto> screenings = service.getScreeningsByDate(date);
        return ResponseEntity.status(HttpStatus.OK).body(screenings);}

    @GetMapping("/all")
    public ResponseEntity<List<ScreeningResponseDto>> findAll() {
        List<ScreeningResponseDto> allScrening = service.findAllScreenings();
        return ResponseEntity.status(HttpStatus.OK).body(allScrening);
    }

    }


package com.cinema.screening;

import com.cinema.screening.dto.ScreeningRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/screenings")
public class ScreeningController {

    private final ScreeningService service;

    @PostMapping("/add")
    public ResponseEntity<Screening> saveScreening(@RequestBody ScreeningRequestDto screeningDto) {
        return new ResponseEntity<>(service.saveScreening(screeningDto), HttpStatus.CREATED);
    }
}

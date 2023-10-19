package com.cinema.screening;

import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<Screening> saveScreening(@RequestBody ScreeningRequestDto screeningDto){
        return new ResponseEntity<>(service.save)
    }
}

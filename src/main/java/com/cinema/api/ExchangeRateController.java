package com.cinema.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rate")
public class ExchangeRateController {

    private final ExchangeRateService service;

    @GetMapping()
    public ResponseEntity<Void> saveEuroRate() {
        service.requestToNBP();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}

package com.cinema.api;

import com.cinema.api.dto.ExchangeRateResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/codes")
public class ExchangeRateController {
    private final ExchangeRateService service;

    @GetMapping()
    public ResponseEntity<List<ExchangeRateResponseDto>> findAllCurrencies() {
        List<ExchangeRateResponseDto> allCurrencies = service.findAllCurrency();
        return ResponseEntity.status(HttpStatus.OK).body(allCurrencies);
    }
}

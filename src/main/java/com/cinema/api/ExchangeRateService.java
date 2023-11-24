package com.cinema.api;

import com.cinema.api.dto.CurrencyData;
import com.cinema.api.dto.ExchangeRateResponseDto;
import com.cinema.api.dto.Rate;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
class ExchangeRateService {

    private final ExchangeRateRepository repository;
    private final ExchangeRateMapper mapper;

    @Value("${nbp.api.url}")
    private String url;

    public void requestToNBP() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        String json = response.getBody();
        saveCurrencyRatesFromJson(json);
    }

    public void saveCurrencyRatesFromJson(String jsonResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<CurrencyData> currencyDataList = objectMapper.readValue(jsonResponse, new TypeReference<List<CurrencyData>>() {
            });

            for (CurrencyData currencyData : currencyDataList) {
                for (Rate rate : currencyData.rates()) {
                    Optional<ExchangeRate> existingExchangeRate = Optional.ofNullable(repository.findByCode(rate.code()));

                    if (existingExchangeRate.isPresent()) {
                        ExchangeRate existing = existingExchangeRate.get();
                        existing.setMid(rate.mid());
                        repository.save(existing);
                        log.info("Update {} rate ", existing.getCode());
                    } else {
                        ExchangeRate exchangeRate = ExchangeRate.builder()
                                .code(rate.code())
                                .mid(rate.mid())
                                .build();
                        repository.save(exchangeRate);
                        log.info("Created new {} rate ", exchangeRate.getCode());
                    }
                }
            }
            addPLNRate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<ExchangeRateResponseDto> findAllCurrency() {
        log.info("Returning all currency");
        return repository.findAll().stream()
                .map(mapper::entityToDto)
                .collect(Collectors.toList());
    }

    public void addPLNRate() {
        ExchangeRate plnExchangeRate = repository.findByCode("PLN");
        if (plnExchangeRate == null) {
            ExchangeRate newPlnExchangeRate = ExchangeRate.builder()
                    .code("PLN")
                    .mid(1.0)
                    .build();
            repository.save(newPlnExchangeRate);
            log.info("Created new PLN rate   ");
        }
    }

}

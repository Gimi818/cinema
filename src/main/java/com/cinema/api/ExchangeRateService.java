package com.cinema.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@AllArgsConstructor
@Log4j2
public class ExchangeRateService {
    private final ExchangeRateRepository repository;

    public void requestToNBP() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://api.nbp.pl/api/exchangerates/tables/a?format=json";
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
                    Optional<ExchangeRate> existingExchangeRate = repository.findByCode(rate.code());

                    if (existingExchangeRate.isPresent()) {
                        ExchangeRate existing = existingExchangeRate.get();
                        existing.setMid(rate.mid());
                        repository.save(existing);
                        log.info("Update exchange rate {}", existing.getCode());
                    } else {
                        ExchangeRate exchangeRate = ExchangeRate.builder()
                                .code(rate.code())
                                .mid(rate.mid())
                                .build();
                        repository.save(exchangeRate);
                        log.info("Created new rate {}", exchangeRate.getCode());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

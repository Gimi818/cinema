package com.cinema.api.dto;

import com.cinema.api.ExchangeRate;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface ExchangeRateMapper {

   ExchangeRateResponseDto entityToDto(ExchangeRate exchangeRate);

}

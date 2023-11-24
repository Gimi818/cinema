package com.cinema.api;

import com.cinema.api.ExchangeRate;
import com.cinema.api.dto.ExchangeRateResponseDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
 interface ExchangeRateMapper {

   ExchangeRateResponseDto entityToDto(ExchangeRate exchangeRate);

}

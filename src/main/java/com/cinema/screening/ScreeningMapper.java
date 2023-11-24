package com.cinema.screening;


import com.cinema.screening.dto.CreatedScreeningDto;
import com.cinema.screening.dto.ScreeningAvailableSeats;
import com.cinema.screening.dto.ScreeningRequestDto;
import com.cinema.screening.dto.ScreeningResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;


@Mapper(componentModel = "spring")
@Component
interface ScreeningMapper {
    ScreeningMapper screeningMapper = Mappers.getMapper(ScreeningMapper.class);

    ScreeningResponseDto entityToDto(Screening screening);

    ScreeningAvailableSeats screeningToSeatsDto(Screening screening);

    Screening dtoToEntity(ScreeningRequestDto screeningRequestDto);

    CreatedScreeningDto createdEntityToDto(Screening screening);


}

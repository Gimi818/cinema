package com.cinema.screening;


import com.cinema.screening.dto.ScreeningRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;


@Mapper(componentModel = "spring")
@Component
public interface ScreeningMapper {
    ScreeningMapper screeningMapper = Mappers.getMapper(ScreeningMapper.class);


    Screening dtoToEntity(ScreeningRequestDto screeningRequestDto);

}

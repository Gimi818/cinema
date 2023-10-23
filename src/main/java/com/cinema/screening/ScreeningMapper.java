package com.cinema.screening;


import com.cinema.screening.dto.ScreeningRequestDto;
import com.cinema.screening.dto.ScreeningResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;


@Mapper(componentModel = "spring")
@Component
public interface ScreeningMapper {
    ScreeningMapper screeningMapper = Mappers.getMapper(ScreeningMapper.class);

    ScreeningResponseDto entityToDto(Screening screening);
//    @ValueMappings({
//            @ValueMapping(source = "time", target = "time"),
//
//    })
    Screening dtoToEntity(ScreeningRequestDto screeningRequestDto);

}

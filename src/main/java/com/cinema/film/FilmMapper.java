package com.cinema.film;

import com.cinema.film.dto.FilmRequestDto;
import com.cinema.film.dto.FilmResponseDto;


import com.cinema.film.filmCategory.FilmCategory;
import org.mapstruct.Mapper;


import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface FilmMapper {

    FilmMapper filmMapper = Mappers.getMapper(FilmMapper.class);

    FilmResponseDto entityToDto(Film film);


    Film dtoToEntity(FilmRequestDto filmRequestDto);



}



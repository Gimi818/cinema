package com.cinema.user;

import com.cinema.user.dto.CreatedUserDto;
import com.cinema.user.dto.UserRequestDto;
import com.cinema.user.dto.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;


@Mapper(componentModel = "spring")
@Component
public interface UserMapper {

    UserMapper userMapper = Mappers.getMapper(UserMapper.class);


    User dtoToEntity(UserRequestDto requestDto);

    UserResponseDto entityToDto(User user);

    CreatedUserDto createdEntityToDto(User user);

}

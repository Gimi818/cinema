package com.cinema.user;

import com.cinema.user.dto.CreatedUserDto;
import com.cinema.user.dto.UserRequestDto;
import com.cinema.user.dto.UserResponseDto;

import java.util.UUID;

public interface UserFacade {
    CreatedUserDto registration(UserRequestDto requestDto);


    User findByUuid(UUID userUuid);

    UserResponseDto findUserByUuid(UUID userUuid);


}

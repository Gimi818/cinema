package com.cinema.user;

import com.cinema.user.dto.CreatedUserDto;
import com.cinema.user.dto.UserRequestDto;
import com.cinema.user.dto.UserResponseDto;

public interface UserFacade {
    CreatedUserDto registration(UserRequestDto requestDto);
    User findById(Long userId);
    UserResponseDto findUserById(Long userId);
}

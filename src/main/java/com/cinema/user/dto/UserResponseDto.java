package com.cinema.user.dto;

import com.cinema.user.userEnum.AccountType;
import com.cinema.user.userEnum.UserRole;

public record UserResponseDto(String firstName,
                              String lastName,
                              String email,
                              AccountType accountType
) {
}

package com.cinema.user.dto;

import com.cinema.user.AccountType;
import com.cinema.user.UserRole;

public record UserResponseDto(Long id ,String firstName,
                              String lastName,
                              String email,
                              UserRole role,
                              AccountType accountType) {
}

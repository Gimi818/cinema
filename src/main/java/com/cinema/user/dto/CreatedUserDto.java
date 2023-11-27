package com.cinema.user.dto;

import java.util.UUID;

public record CreatedUserDto(
        UUID uuid,
        String firstName,
        String lastName,
        String email) {
}

package com.spotify.app.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UserRequest(
        @NotBlank
        String firstName,
        @NotBlank
        String lastName,

        @NotBlank
        String email,
        String password,
        String gender,
        int day,
        int month,
        int year,
        String roleName
) {
}

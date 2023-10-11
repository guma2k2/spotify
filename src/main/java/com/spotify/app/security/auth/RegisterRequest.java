package com.spotify.app.security.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public record RegisterRequest(@NotBlank String firstName,
                              @NotBlank String lastName,
                              @Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\\\.[A-Za-z0-9-]+)*(\\\\.[A-Za-z]{2,})$")
                              @NotBlank String email,

                              @Size(min = 8, message = "the password must be at least 8 characters")
                              String password,
                              int day,
                              @NotBlank
                              int month,
                              int year,
                              @NotBlank
                              String gender
) {

}

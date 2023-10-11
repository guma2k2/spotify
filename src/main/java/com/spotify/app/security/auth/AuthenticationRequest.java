package com.spotify.app.security.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public record AuthenticationRequest (@Email  String email,
                                     @Size(min = 8, message = "the password must be at least 8 characters")
                                     String password
) {

}
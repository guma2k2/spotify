package com.spotify.app.security.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.spotify.app.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    @JsonProperty("access_token")
    private String accessToken;


    @JsonProperty("refresh_token")
    private String refreshToken ;

    private UserDTO user ;

}
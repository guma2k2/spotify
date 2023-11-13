package com.spotify.app.journey;

import com.github.javafaker.Faker;
import com.spotify.app.AbstractTestcontainers;
import com.spotify.app.dto.response.SongResponse;
import com.spotify.app.security.auth.AuthenticationRequest;
import com.spotify.app.security.auth.AuthenticationResponse;
import com.spotify.app.security.jwt.JwtService;
import com.spotify.app.service.PlaylistUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class PlaylistIT extends AbstractTestcontainers {

    private static final String AUTH_PATH = "/api/v1/auth";
    private static final String REVIEW_PATH = "/api/v1/playlist";
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PlaylistUserService playlistUserService;

    @Autowired
    private JwtService jwtService;

    @Test
    public void canUserCreatePlaylist() {
        String email = "taylor@gmail.com";
        String password = "thuan2023";
        AuthenticationRequest authRequest = new AuthenticationRequest(email,password);
        AuthenticationResponse authResponse = restTemplate.postForObject(AUTH_PATH+"/authenticate", authRequest, AuthenticationResponse.class);
        log.info(String.valueOf(authResponse));
        String token = authResponse.getAccessToken();

        Long userId = authResponse.getUser().id();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.ACCEPT , MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.set("Authorization", "Bearer " + token);

        HttpEntity<?> request = new HttpEntity<>(httpHeaders);

        String url = String.format(REVIEW_PATH.concat("/%d/create/playlist"),userId);

        log.info(url);
        // when
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET ,request, String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);


    }
}

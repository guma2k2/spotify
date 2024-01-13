package com.spotify.app.journey;

import com.github.javafaker.Faker;
import com.spotify.app.AbstractTestcontainers;
import com.spotify.app.dto.AlbumDTO;
import com.spotify.app.dto.request.AlbumRequest;
import com.spotify.app.dto.response.UserNoAssociationResponse;
import com.spotify.app.security.auth.AuthenticationRequest;
import com.spotify.app.security.auth.AuthenticationResponse;
import com.spotify.app.security.jwt.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class AlbumIT extends AbstractTestcontainers {

    private static final String AUTH_PATH = "/api/v1/auth";
    private static final String ALBUM_PATH = "/api/v1/album";

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private JwtService jwtService;

    @Test
    public void canArtistAddAlbum() {
        // given
        Faker faker = new Faker();
        String email = "taylor@gmail.com";
        String password = "thuan2023";
        AuthenticationRequest authRequest = new AuthenticationRequest(email,password);
        AuthenticationResponse authResponse = restTemplate.postForObject(AUTH_PATH+"/authenticate", authRequest, AuthenticationResponse.class);
        log.info(String.valueOf(authResponse));
        String token = authResponse.getAccessToken();


        Long userId = authResponse.getUser().id();

        AlbumRequest albumRequest = new AlbumRequest(faker.funnyName().name());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.ACCEPT , MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.set("Authorization", "Bearer " + token);

        HttpEntity<?> request = new HttpEntity<>(albumRequest, httpHeaders);

        String url = String.format(ALBUM_PATH.concat("/%d/add"),userId);
        log.info(url);

        // when
        ResponseEntity<AlbumDTO> response = restTemplate.exchange(url, HttpMethod.POST ,request, AlbumDTO.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }
}

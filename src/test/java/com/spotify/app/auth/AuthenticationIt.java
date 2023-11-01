package com.spotify.app.auth;


import com.github.javafaker.Faker;
import com.spotify.app.model.User;
import com.spotify.app.security.auth.AuthenticationRequest;
import com.spotify.app.security.auth.AuthenticationResponse;
import com.spotify.app.security.auth.RegisterRequest;
import com.spotify.app.security.jwt.JwtService;
import com.spotify.app.service.UserService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Random;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@Slf4j
public class AuthenticationIt  {

    @Autowired
    private  TestRestTemplate restTemplate;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;
    private static final String AUTH_PATH = "/api/v1/auth";
    private static final Random random = new Random();


    // register
    @Test
    public void canRegister() {
        // given
        Faker faker = new Faker();

        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = faker.name().firstName() + UUID.randomUUID() + "@gmail.com";
        String password = "passwordd";
        int day = 1 ;
        int month = 11;
        int year = 2002;
        String gender = random.nextInt(2,3) % 2 == 0 ? "MALE" : "FEMALE";
        RegisterRequest request = new RegisterRequest(firstName,lastName ,email,password,day,month,year,gender);
        AuthenticationResponse response = restTemplate.postForObject(AUTH_PATH+"/register", request, AuthenticationResponse.class);

        assertThat(response.getUser().email()).isEqualTo(email) ;
        assertThat(response.getUser().firstName()).isEqualTo(firstName) ;
        assertThat(response.getUser().lastName()).isEqualTo(lastName) ;
//        assertThat(jwtService.isTokenValid(response.getAccessToken(), response.getUser()));
    }


    // login
    @Test
    public void canLogin() {
        String email = "taylor@gmail.com";
        String password = "thuan2023";
        AuthenticationRequest request = new AuthenticationRequest(email,password);
        AuthenticationResponse response = restTemplate.postForObject(AUTH_PATH+"/authenticate", request, AuthenticationResponse.class);
        assertThat(response.getUser().email()).isEqualTo(email) ;
        assertThat(response.getAccessToken()).isNotNull() ;
    }

    // refresh Token
    @Test
    public void canRefreshToken() {
        // given
        String email = "taylor@gmail.com";
        String password = "thuan2023";
        AuthenticationRequest request = new AuthenticationRequest(email,password);
        AuthenticationResponse authResponse = restTemplate.postForObject(AUTH_PATH+"/authenticate", request, AuthenticationResponse.class);
        String refreshToken = authResponse.getRefreshToken();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        log.info(refreshToken);
        httpHeaders.set("Authorization", "Bearer " + refreshToken);

        HttpEntity<?> requestEntity = new HttpEntity<>( httpHeaders);
        // when
        AuthenticationResponse response = restTemplate.postForObject(AUTH_PATH+"/refresh-token", requestEntity, AuthenticationResponse.class);

        // then
        User user = userService.get(authResponse.getUser().id());
        log.info(String.valueOf(response));

        assertThat(jwtService.isTokenValid(response.getRefreshToken(),user)).isTrue();
        assertThat(jwtService.isTokenValid(response.getAccessToken(),user)).isTrue();
    }
    // login fail return exception

    @Test
    public void shouldNotLogin() {
            String email = "taylor@gmail.com";
            String password = "thuan202333";
            AuthenticationRequest request = new AuthenticationRequest(email,password);
        ResponseEntity<AuthenticationResponse> response = restTemplate.postForEntity(AUTH_PATH + "/authenticate", request, AuthenticationResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

}

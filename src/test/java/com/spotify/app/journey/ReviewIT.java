//package com.spotify.app.journey;
//
//import com.github.javafaker.Faker;
//import com.spotify.app.AbstractTestcontainers;
//import com.spotify.app.dto.ReviewDTO;
//import com.spotify.app.dto.response.ReviewResponse;
//import com.spotify.app.dto.response.SongResponse;
//import com.spotify.app.security.auth.AuthenticationRequest;
//import com.spotify.app.security.auth.AuthenticationResponse;
//import com.spotify.app.security.jwt.JwtService;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.http.*;
//
//import java.util.List;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Slf4j
//public class ReviewIT extends AbstractTestcontainers {
//
//    private static final String AUTH_PATH = "/api/v1/auth";
//    private static final String REVIEW_PATH = "/api/v1/review";
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    @Autowired
//    private JwtService jwtService;
//
//    @Test
//    public void canReviewInSong() {
//
//        // given
//        Faker faker = new Faker();
//        String email = "taylor@gmail.com";
//        String password = "thuan2023";
//        AuthenticationRequest authRequest = new AuthenticationRequest(email,password);
//        AuthenticationResponse authResponse = restTemplate.postForObject(AUTH_PATH+"/authenticate", authRequest, AuthenticationResponse.class);
//        log.info(String.valueOf(authResponse));
//        String token = authResponse.getAccessToken();
//
//        Long userId = authResponse.getUser().id();
//        Long songId = 1L;
//        String reviewContent = faker.animal().name();
//
//        ReviewDTO reviewRequest = new ReviewDTO(0L,reviewContent);
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.set(HttpHeaders.ACCEPT , MediaType.APPLICATION_JSON_VALUE);
//        httpHeaders.set("Authorization", "Bearer " + token);
//
//        HttpEntity<?> request = new HttpEntity<>(reviewRequest,httpHeaders);
//
//        String urlRequest = String.format(REVIEW_PATH.concat("/%d/review/in/%d"),userId,songId);
//
//        log.info(urlRequest);
//        // when
//        ResponseEntity<ReviewResponse[]> response = restTemplate.exchange(urlRequest, HttpMethod.POST ,request, ReviewResponse[].class);
//        // then
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//    }
//}

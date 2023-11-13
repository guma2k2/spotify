//package com.spotify.app.journey;
//
//import com.spotify.app.AbstractTestcontainers;
//import com.spotify.app.dto.response.UserNoAssociationResponse;
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
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Slf4j
//public class FollowerIT extends AbstractTestcontainers {
//
//    private static final String AUTH_PATH = "/api/v1/auth";
//    private static final String FOLLOWER_PATH = "/api/v1/follower";
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//    @Autowired
//    private JwtService jwtService;
//
//
//    @Test
//    public void canFollowUser() {
//        // given
//        Long currentUserId ;
//        Long targetUserId = 1L;
//
//
//        String email = "taylor@gmail.com";
//        String password = "thuan2023";
//        AuthenticationRequest authRequest = new AuthenticationRequest(email,password);
//        AuthenticationResponse authResponse = restTemplate.postForObject(AUTH_PATH+"/authenticate", authRequest, AuthenticationResponse.class);
//        log.info(String.valueOf(authResponse));
//        String token = authResponse.getAccessToken();
//
//        currentUserId = authResponse.getUser().id();
//
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.set(HttpHeaders.ACCEPT , MediaType.APPLICATION_JSON_VALUE);
//        httpHeaders.set("Authorization", "Bearer " + token);
//
//        HttpEntity<?> request = new HttpEntity<>(httpHeaders);
//
//        String url = String.format(FOLLOWER_PATH.concat("/%d/follow/%d"),currentUserId,targetUserId);
//
//        log.info(url);
//        // when
//        ResponseEntity<UserNoAssociationResponse[]> response = restTemplate.exchange(url, HttpMethod.GET ,request, UserNoAssociationResponse[].class);
//
//        // then
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//    }
//
//}

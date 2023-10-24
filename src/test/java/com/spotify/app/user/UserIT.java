package com.spotify.app.user;

import com.spotify.app.AbstractTestcontainers;
import com.spotify.app.dto.response.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class UserIT {

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String USER_PATH = "/api/v1/user";

    @Test
    public void shouldReturnUser__WhenGivenValidId() {
        // given
        Long userId = 1l;
        String url = USER_PATH.concat(String.format("/%d",userId));
//        log.info(url);
        ResponseEntity<UserResponse> response = restTemplate.
                exchange(url, HttpMethod.GET,null,UserResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void shouldReturnFail__WhenGivenUnValidId(){
        Long userId = 0l ;
        String url = USER_PATH.concat(String.format("/%d",userId));
//        log.info(url);
        ResponseEntity<UserResponse> response = restTemplate.
                exchange(url, HttpMethod.GET,null,UserResponse.class);
//        log.info(String.valueOf(response.getBody()));
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

}

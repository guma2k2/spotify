//package com.spotify.app.journey;
//
//import com.spotify.app.AbstractTestcontainers;
//import com.spotify.app.dto.UserDTO;
//import com.spotify.app.dto.response.UserResponse;
//import com.spotify.app.security.jwt.JwtService;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Slf4j
//public class UserIT  {
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    @Autowired
//    private JwtService jwtService;
//
//    private static final String USER_PATH = "/api/v1/user";
//
//    @Test
//    public void shouldReturnUser__WhenGivenValidId() {
//        // given
//        Long userId = 1L;
//        String url = USER_PATH.concat(String.format("/%d",userId));
////        log.info(url);
//        ResponseEntity<UserDTO> response = restTemplate.
//                exchange(url, HttpMethod.GET,null,UserDTO.class);
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(response.getBody()).isNotNull();
//    }
//
////    @Test
////    public void shouldReturnFail__WhenGivenUnValidId(){
////        Long userId = 0L ;
////        String url = USER_PATH.concat(String.format("/%d",userId));
//////        log.info(url);
////        ResponseEntity<UserDTO> response = restTemplate.
////                getForEntity(url,UserDTO.class);
//////        log.info(String.valueOf(response.getBody()));
////        log.info(String.valueOf(response.getStatusCode()));
////    }
//
//}

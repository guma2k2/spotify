package com.spotify.app.journey;

import com.github.javafaker.Faker;
import com.spotify.app.AbstractTestcontainers;
import com.spotify.app.dto.request.SongRequest;
import com.spotify.app.dto.response.SongResponse;
import com.spotify.app.enums.Genre;
import com.spotify.app.model.Song;
import com.spotify.app.repository.SongRepository;
import com.spotify.app.security.auth.AuthenticationRequest;
import com.spotify.app.security.auth.AuthenticationResponse;
import com.spotify.app.security.jwt.JwtService;
import com.spotify.app.service.SongService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class SongIT extends AbstractTestcontainers {
    private static final String SONG_PATH = "/api/v1/song";
    private static final String AUTH_PATH = "/api/v1/auth";

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private SongService songService;

    @Autowired
    private SongRepository songRepository;
    @Test
    public void canSaveSong__WhenArtistAct() {
        // given
        Faker faker = new Faker();
        String email = "taylor@gmail.com";
        String password = "thuan2023";
        AuthenticationRequest authRequest = new AuthenticationRequest(email,password);
        AuthenticationResponse authResponse = restTemplate.postForObject(AUTH_PATH+"/authenticate", authRequest, AuthenticationResponse.class);
        String token = authResponse.getAccessToken();

        String name = faker.funnyName().name();
        String genre = Genre.Rock.toString();
        int duration = 200 ;
        String lyric = "<div>lyric</div>";
        int month = 12 ;
        int day = 12 ;
        int year = 2022;
        String label = "happy";
        Long userId = authResponse.getUser().id();

        SongRequest songRequest = new SongRequest(name, genre, duration,lyric,day,month,year,label,userId);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.ACCEPT , MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.set("Authorization", "Bearer " + token);



        HttpEntity<?> request = new HttpEntity<>(songRequest,httpHeaders);
        // when
        ResponseEntity<SongResponse> response = restTemplate.exchange(SONG_PATH + "/save", HttpMethod.POST ,request, SongResponse.class);
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void canUpdateSong() {
        // given
        Faker faker = new Faker();
        String email = "taylor@gmail.com";
        String password = "thuan2023";
        AuthenticationRequest authRequest = new AuthenticationRequest(email,password);
        AuthenticationResponse authResponse = restTemplate.postForObject(AUTH_PATH+"/authenticate", authRequest, AuthenticationResponse.class);
        String token = authResponse.getAccessToken();
        Song underUpdate = songService.get(1L);

        String updateName = faker.funnyName().name();
        int year = 2023;
        int month = 3 ;
        int day = 30 ;

        SongRequest songRequest = new SongRequest(
                updateName,
                underUpdate.getGenre().toString(),
                underUpdate.getDuration(),
                underUpdate.getLyric(),
                day,month,year,
                underUpdate.getLabel(),
                authResponse.getUser().id());
        // when
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.ACCEPT , MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.set("Authorization", "Bearer " + token);
        HttpEntity<?> request = new HttpEntity<>(songRequest,httpHeaders);
        ResponseEntity<SongResponse> response = restTemplate.exchange(SONG_PATH + "/update/" + underUpdate.getId(), HttpMethod.PUT ,request, SongResponse.class);
        // then
        SongResponse actual = response.getBody();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actual.name()).isEqualTo(updateName);
        assertThat(actual.label()).isEqualTo(underUpdate.getLabel());
    }

    @Test
    public void canUpdateStatusSong() {

        // given
        String email = "taylor@gmail.com";
        String password = "thuan2023";
        AuthenticationRequest authRequest = new AuthenticationRequest(email,password);
        AuthenticationResponse authResponse = restTemplate.postForObject(AUTH_PATH+"/authenticate", authRequest, AuthenticationResponse.class);
        String token = authResponse.getAccessToken();
        Song underUpdate = songService.get(1L);
        boolean expected = !underUpdate.isStatus();
        // when
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.ACCEPT , MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.set("Authorization", "Bearer " + token);
        HttpEntity<?> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<SongResponse> response = restTemplate.exchange(SONG_PATH + "/status/" + underUpdate.getId(), HttpMethod.PUT ,request, SongResponse.class);

        // then
        boolean actual = response.getBody().status();
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    public void canAddCollapse() {
        // given
        String email = "taylor@gmail.com";
        String password = "thuan2023";
        AuthenticationRequest authRequest = new AuthenticationRequest(email,password);
        AuthenticationResponse authResponse = restTemplate.postForObject(AUTH_PATH+"/authenticate", authRequest, AuthenticationResponse.class);
        String token = authResponse.getAccessToken();
        Song underTest = songRepository.findByIdReturnUsersAlbumsReviews(1L).get();

        Long addUserId = 2L ;
        int expectSize = underTest.getUsers().size() + 1;

        boolean checkExist = underTest.getUsers().stream().anyMatch(user -> user.getId().equals(addUserId));
        // when
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.ACCEPT , MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.set("Authorization", "Bearer " + token);
        HttpEntity<?> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<SongResponse> response = restTemplate.exchange(SONG_PATH + "/1/add/"+addUserId, HttpMethod.GET ,request, SongResponse.class);
        // then
        assertThat(checkExist).isFalse();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().users().size()).isEqualTo(expectSize);
    }
    @Test
    public void canRemoveCollapse() {
        // given
        String email = "taylor@gmail.com";
        String password = "thuan2023";
        AuthenticationRequest authRequest = new AuthenticationRequest(email,password);
        AuthenticationResponse authResponse = restTemplate.postForObject(AUTH_PATH+"/authenticate", authRequest, AuthenticationResponse.class);
        String token = authResponse.getAccessToken();
        Song underTest = songRepository.findByIdReturnUsersAlbumsReviews(1L).get();

        Long removeUserId = underTest.getUsers().stream().filter(user -> user.getId() > 0).findFirst().get().getId();
        int expectSize = underTest.getUsers().size() - 1;
        // when
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.ACCEPT , MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.set("Authorization", "Bearer " + token);
        HttpEntity<?> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<SongResponse> response = restTemplate.exchange(SONG_PATH + "/1/remove/" + removeUserId, HttpMethod.GET ,request, SongResponse.class);
        // then
        assertThat(removeUserId).isGreaterThan(0);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().users().size()).isEqualTo(expectSize);
    }
}

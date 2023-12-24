package com.spotify.app.song;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotify.app.controller.SongController;
import com.spotify.app.dto.request.SongRequest;
import com.spotify.app.dto.response.SongResponse;
import com.spotify.app.service.SongService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;

@WebMvcTest(
        controllers = SongController.class,
        excludeAutoConfiguration = {
                UserDetailsServiceAutoConfiguration.class, SecurityAutoConfiguration.class
        })

public class SongControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SongService songService;

    @Autowired
    private ObjectMapper objectMapper;

    private String urlPrefix = "/api/v1/song";

    private SongRequest songRequest;
    @BeforeEach
    public void setUp () {
        songRequest = new SongRequest(
                "song_name",
                "CLASSICAL",
                2,
                "lyric",
                12,
                12,
                2023,
                "happy",
                1L
        );
    }
    @Test
    public void canSaveSong () throws Exception {
        // given
        Long savedSongId = 1L;
        SongResponse expectResponse = new SongResponse(1L);
        String url = urlPrefix.concat("/save");
        String requestJson = objectMapper.writeValueAsString(songRequest);

        // when
        when(songService.saveSong(songRequest)).thenReturn(savedSongId);
        when(songService.getById(savedSongId)).thenReturn(expectResponse);

        // then
        this.mockMvc
                .perform(post(url).contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", Matchers.is(expectResponse)))
                .andExpect(jsonPath("$.id", Matchers.is(1)));
    }

//    @Test
//    public void shouldThrowExceptionWhenSaveSong () throws Exception {
//        // given
//        Long savedSongId = 1L;
//        SongResponse expectResponse = new SongResponse(1L);
//        String url = urlPrefix.concat("/save");
//        String requestJson = objectMapper.writeValueAsString(songRequest);
//        Map< String, String > errors = new HashMap<>();
//        errors.put("nane","name of song must not be greater than 50 char");
//        // when
//        when(songService.saveSong(songRequest)).thenThrow(new MethodArgumentNotValidException(errors));
//        when(songService.getById(savedSongId)).thenReturn(expectResponse);
//
//        // then
//        this.mockMvc
//                .perform(post(url).contentType(MediaType.APPLICATION_JSON).content(requestJson))
//                .andDo(print())
//                .andExpect(status().isBadRequest());
//    }

    @Test
    public void canUpdateSong () throws Exception {
        Long songId = 1L;
        SongResponse expectResponse = new SongResponse(1L);
        String url = urlPrefix.concat("/update/" + songId);
        String requestJson = objectMapper.writeValueAsString(songRequest);

        // when
        when(songService.getById(songId)).thenReturn(expectResponse);

        // then
        this.mockMvc
                .perform(put(url).contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(1)));
    }

    @Test
    public void canGetById () throws Exception {
        Long songId = 1L;
        SongResponse expectResponse = new SongResponse(1L);
        when(songService.getById(songId)).thenReturn(expectResponse);
        String url = urlPrefix.concat("/" + songId);
        this.mockMvc.perform(get(url)).andExpect(status().isOk());
    }
}

package com.spotify.app.playlist;


import com.spotify.app.controller.PlaylistController;
import com.spotify.app.dto.PlaylistDTO;
import com.spotify.app.service.PlaylistService;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URLEncoder;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(
        controllers = PlaylistController.class,
        excludeAutoConfiguration = {
                UserDetailsServiceAutoConfiguration.class, SecurityAutoConfiguration.class
        })
@Slf4j
public class PlaylistControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PlaylistService playlistService;

    private PlaylistDTO playlistDTO;

    private String urlPrefix = "/api/v1/playlist";


//    List<SongResponse> songs
    @BeforeEach
    void setUp () {
        playlistDTO = new PlaylistDTO(1L,
                "playlist_name",
                "desc",
                "image.png",
                "thumbnail.png",
                2,
                "",
                0l,null);
    }

    // can save playlist
    @Test
    public void canSavePlaylist () throws Exception {
        // given
        String name = "playlist_name";
        String desc = "desc";
        name = URLEncoder.encode(name, "UTF-8");
        desc = URLEncoder.encode(desc, "UTF-8");
        String url = urlPrefix.concat(String.format("/admin/save?description=%s&name=%s",desc,name));
        log.info(url);

        // when
        when(playlistService.addPlaylist(desc, name)).thenReturn(1L);
        when(playlistService.findByIdReturnSongs(any())).thenReturn(playlistDTO);
        // then
        this.mockMvc.perform(post(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.name", Matchers.is(name)));
    }
    //
//    @Test
//    public void canUpdatePlaylist () {
//
//    }
}

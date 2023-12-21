package com.spotify.app.playlist;


import com.spotify.app.controller.PlaylistController;
import com.spotify.app.dto.PlaylistDTO;
import com.spotify.app.dto.response.SongResponse;
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

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
        String url = urlPrefix.concat(String.format("/admin/save?description=%s&name=%s", desc, name));

        // when
        when(playlistService.addPlaylist(desc, name)).thenReturn(1L);
        when(playlistService.findByIdReturnSongs(any())).thenReturn(playlistDTO);

        // then
        this.mockMvc.perform(post(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.name", Matchers.is(name)));
    }

    @Test
    public void canUpdatePlaylist () throws Exception {
        // given
        Long updateId = 1L;
        String updateName = "playlist_name_will_be_updated";
        String updateDESC = "desc";
        String url = urlPrefix.concat(String.format("/admin/update/%d?description=%s&name=%s",updateId, updateDESC, updateName));
        PlaylistDTO updatedPlaylistDTO = new PlaylistDTO(1L,
                updateName,
                updateDESC,
                "image.png",
                "thumbnail.png",
                2,
                "",
                0l,null);
        when(playlistService.findByIdReturnSongs(any())).thenReturn(updatedPlaylistDTO);

        // then
        this.mockMvc.perform(put(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.name", Matchers.is(updateName)));
    }

    @Test
    public void canPlaylistAddSong () throws Exception {
        // given
        String name = "playlist_name";
        String desc = "desc";
        Long playlistId = 1L;
        Long songId = 1L;
        String url = urlPrefix.concat(String.format("/%d/add/song/%d", playlistId, songId));
        List<SongResponse> songs = List.of(new SongResponse(1L));
        PlaylistDTO playlistDTO = new PlaylistDTO(1L,
                name,
                desc,
                "image.png",
                "thumbnail.png",
                2,
                "",
                0l,songs);

        // when
        when(playlistService.findByIdReturnSongs(any())).thenReturn(playlistDTO);

        // then
        this.mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.songs", hasSize(1)))
                .andExpect(jsonPath("$.songs[0].id", Matchers.is(1)));


    }
}

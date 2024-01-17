package com.spotify.app.album;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotify.app.controller.AlbumController;
import com.spotify.app.dto.AlbumDTO;
import com.spotify.app.dto.request.AlbumRequest;
import com.spotify.app.model.User;
import com.spotify.app.security.auth.AuthUserDetails;
import com.spotify.app.service.AlbumService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = AlbumController.class,
        excludeAutoConfiguration = {
                UserDetailsServiceAutoConfiguration.class, SecurityAutoConfiguration.class
        }
)
public class AlbumControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private AlbumService albumService;

    private final String prefixUrl = "/api/v1/album";


    private AlbumDTO albumDTO ;

    @BeforeEach
    public void beforeEach () {
        albumDTO = new AlbumDTO(1L, "test","2023", null, "", "", 0,"4:12", true, null);
    }
    @Test
    void canGetAlbumById () throws Exception {
        // when
        when(albumService.findById(1L)).thenReturn(albumDTO);

        // then
        this.mockMvc.perform(get(prefixUrl+"/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.notNullValue(AlbumDTO.class)))
                .andExpect(jsonPath("$.name", Matchers.is(albumDTO.name())))
                .andExpect(jsonPath("$.releaseDate", Matchers.is(albumDTO.releaseDate())));
    }

    // save album
    @Test
    void canAddAlbumByArtistId () throws Exception {
        // Mock data
        String albumName = "album_name";
        AlbumRequest request = new AlbumRequest(albumName); // Provide necessary data for the album request
        Long savedAlbumId = 1L;
        AlbumDTO savedAlbum = new AlbumDTO(1L, request.name(),"", null, "", "", 0,"", true, null);
        // Mock behavior of the service
        when(albumService.addAlbum(any(AuthUserDetails.class), any(AlbumRequest.class))).thenReturn(savedAlbumId);
        when(albumService.findById(savedAlbumId)).thenReturn(savedAlbum); // Provide necessary data for the response

        // Perform the request and verify the result
        mockMvc.perform(post("/api/v1/album")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .with(user("username")) // Set the username for authentication
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedAlbumId));
//                .andExpect(jsonPath("$.someProperty").value("someValue"));

        // Verify that the service method was called
        verify(albumService, times(1)).addAlbum(any(AuthUserDetails.class), any(AlbumRequest.class));
        verify(albumService, times(1)).findById(savedAlbumId);

    }

    // put album


    // add song
//    @Test
//    void canAddSong () throws Exception {
//        // given
//        Long albumId = 1L;
//        Long songId = 1L;
//        List<SongResponse> songDTOS = List.of(new SongResponse(songId));
//        AlbumDTO expectedResponse = new AlbumDTO(1L, "test","2023", null, "", "", 0,"4:12", true, songDTOS);
//        // when
//        when(albumService.addSong(albumId, songId)).thenReturn(expectedResponse);
//        // then
//        String urlAddSong = String.format("/api/v1/album/%d/add/%d", albumId, songId);
//        this.mockMvc.perform(get(urlAddSong)).andExpect(status().isOk())
//                .andExpect(jsonPath("$", Matchers.notNullValue(AlbumDTO.class)))
//                .andExpect(jsonPath("$.name", Matchers.is("test")))
//        ;
//    }

    // remove song

    // update status
    @Test
    void canUpdateStatus () throws Exception {
        // given
        Long albumId = 1L ;
        String expectResponse = String.format("album with id: %d is disabled", albumId);
        // when
        when(albumService.updateStatusAlbum(albumId)).thenReturn(expectResponse);
        // then
        String urlPutAlbum = String.format("/api/v1/album/update/status/%d", albumId);
        this.mockMvc.perform(put(urlPutAlbum))
                .andExpect(jsonPath("$", Matchers.notNullValue(String.class)))
                .andExpect(status().isOk())
        ;
    }

    // delete album

    @Test
    void canDeleteAlbum () {

    }


}

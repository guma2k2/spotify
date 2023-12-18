package com.spotify.app.playlist;


import com.spotify.app.controller.PlaylistController;
import com.spotify.app.service.PlaylistService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(
        controllers = PlaylistController.class,
        excludeAutoConfiguration = {
                UserDetailsServiceAutoConfiguration.class, SecurityAutoConfiguration.class
        })
public class PlaylistControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PlaylistService playlistService;
    @BeforeEach
    void setUp () {

    }
    //
}

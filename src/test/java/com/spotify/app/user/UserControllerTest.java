package com.spotify.app.user;


import com.spotify.app.controller.UserController;
import com.spotify.app.repository.*;
import com.spotify.app.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @MockBean
    private UserService userService ;



    @Autowired
    private MockMvc mockMvc;
    @Test
    @DisplayName("Hello")
    public void shouldReturnListUserDTO() throws Exception {
        Long userId = 2L ;
        mockMvc.perform(get("/api/v1/user/"+ userId)).andExpect(status().is(200));
    }
}

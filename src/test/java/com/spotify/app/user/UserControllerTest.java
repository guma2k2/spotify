package com.spotify.app.user;


import com.spotify.app.config.MvcConfig;
import com.spotify.app.controller.UserController;
import com.spotify.app.dto.RoleDTO;
import com.spotify.app.dto.response.UserResponse;
import com.spotify.app.enums.Gender;
import com.spotify.app.repository.*;
import com.spotify.app.security.config.SecurityConfig;
import com.spotify.app.security.jwt.JwtService;
import com.spotify.app.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.com.github.dockerjava.core.MediaType;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@ExtendWith(SpringExtension.class)
public class UserControllerTest {

    @MockBean
    private UserService userService ;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnListUserDTO() throws Exception {
        Long userId = 1l ;
        RoleDTO roleDTO = new RoleDTO(1,"ROLE_CUSTOMER");
        UserResponse userResponse =
                new UserResponse(userId,"test",
                        "test",
                        "test test",
                        "test@gmail.com",
                        Gender.MALE,
                        "",
                        roleDTO);

        when(userService.findByIdReturnWithRole(userId)).thenReturn(userResponse);
        mockMvc.perform(get("/api/v1/user/admin/"+ userId))
                .andExpect(status().isOk());
    }
}

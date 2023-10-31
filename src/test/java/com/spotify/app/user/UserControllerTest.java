package com.spotify.app.user;


import com.spotify.app.controller.UserController;
import com.spotify.app.dto.RoleDTO;
import com.spotify.app.dto.response.UserResponse;
import com.spotify.app.enums.Gender;
import com.spotify.app.repository.UserRepository;
import com.spotify.app.security.config.CorsConfig;
import com.spotify.app.security.config.SecurityConfig;
import com.spotify.app.security.config.SecurityFilterChainConfig;
import com.spotify.app.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@Import({SecurityConfig.class, SecurityFilterChainConfig.class, CorsConfig.class})
public class UserControllerTest {

    @MockBean
    private UserService userService ;

    @Autowired
    private UserRepository userRepository;

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
        mockMvc.perform(get("/api/v1/user/1"))
                .andExpect(status().isOk());
    }
}

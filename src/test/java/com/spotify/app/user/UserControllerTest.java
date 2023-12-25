package com.spotify.app.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotify.app.controller.UserController;
import com.spotify.app.dto.RoleDTO;
import com.spotify.app.dto.SongDTO;
import com.spotify.app.dto.UserDTO;
import com.spotify.app.dto.request.UserRequest;
import com.spotify.app.dto.response.UserResponse;
import com.spotify.app.enums.Gender;
import com.spotify.app.service.UserService;
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

import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(
        controllers = UserController.class,
        excludeAutoConfiguration = {
                UserDetailsServiceAutoConfiguration.class, SecurityAutoConfiguration.class
        }
)
public class UserControllerTest {
    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private UserResponse userResponse;
    private UserRequest userRequest;

    private RoleDTO roleDTO;

    private UserDTO userDTO;

    @BeforeEach
    public void setUp () {
        roleDTO = new RoleDTO(1, "ROLE_CUSTOMER");
        userDTO = new UserDTO(1L,
                "firstname",
                "lastname",
                "firstname lastname",
                "email@gmail.com",
                Gender.MALE,
                true,
                roleDTO,
                "photo.png",
                null);
        userResponse = new UserResponse(
                1L,
                "firstname",
                "lastname",
                "firstname lastname",
                "email@gmail.com",
                Gender.MALE,
                "photo.png",
                true,
                "12/12/2023",
                roleDTO
        );
        userRequest = new UserRequest("firstname", "lastname","email@gmail.com","passwordd", "MALE",
                12, 12 , 2023, "ROLE_CUSTOMER");
    }
    // update status
    @Test
    public void canUpdateStatus () throws Exception {
        // given
        Long userId = 1L;
        String status = !userDTO.status() ? "disabled" : "enabled";
        String url = "/api/v1/user/admin/update/status/" + userId;
        String expectResponse = String.format("user with id: %d is ".concat(status),userId);

        // when
        when(userService.updateStatus(userId)).thenReturn(expectResponse);

        // then
        this.mockMvc.perform(put(url)).andExpect(status().isOk()).andExpect(jsonPath("$",Matchers.is(expectResponse)));

    }
    // get by id
    @Test
    public void canGetById () throws Exception {
        // given
        Long userId = 1L;
        String url = "/api/v1/user/" + userId;

        // when
        when(userService.findByIdReturnRoleAndSongs(userId)).thenReturn(userDTO);

        // then
        this.mockMvc.perform(get(url)).andExpect(status().isOk()).andExpect(jsonPath("$.id", Matchers.is(1)));
    }
    // add user

    @Test
    public void canAddUser () throws Exception {
        // given
        String url = "/api/v1/user/admin/save";
        String request = objectMapper.writeValueAsString(userRequest);
        // when
        when(userService.addUser(userRequest)).thenReturn(userResponse);
        // then
        this.mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(request)).andExpect(status().isOk()).andExpect(jsonPath("$.id", Matchers.is(1)));
    }
    // update user
    @Test
    public void canUpdateUser () throws Exception {
        // given
        var expectUserId = 1;
        String url = "/api/v1/user/admin/update/" + expectUserId;
        String request = objectMapper.writeValueAsString(userRequest);
        // when
        when(userService.updateUser(userRequest,1L)).thenReturn(userResponse);
        // then
        this.mockMvc.perform(put(url).contentType(MediaType.APPLICATION_JSON).content(request)).andExpect(status().isOk()).andExpect(jsonPath("$.id", Matchers.is(expectUserId)));
    }
}

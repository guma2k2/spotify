package com.spotify.app.role;

import com.spotify.app.controller.RoleController;
import com.spotify.app.dto.RoleDTO;
import com.spotify.app.service.RoleService;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.List;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;

@WebMvcTest(
        controllers = RoleController.class,
        excludeAutoConfiguration = {
                UserDetailsServiceAutoConfiguration.class, SecurityAutoConfiguration.class
        })
public class RoleControllerTest {

        @Autowired
        private MockMvc mockMvc;
        @MockBean
        private RoleService roleService;
        private RoleDTO role1;
        private RoleDTO role2;

        private String urlPrefix ="/api/v1/role";
        @BeforeEach
        void setUp () {
                role1 = new RoleDTO(1, "CUSTOMER");
                role2 = new RoleDTO(1, "ADMIN");
        }

        @Test
        public void canGetAllRole () throws Exception {
                // given
                List<RoleDTO> expected = List.of(role1, role2);
                // when
                when(roleService.listAll()).thenReturn(expected);
                // then
                this.mockMvc.perform(get(urlPrefix))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$", hasSize(2)))
                        .andExpect(jsonPath("$[0].name", Matchers.is("CUSTOMER")))
                        .andExpect(jsonPath("$[1].name", Matchers.is("ADMIN")));
        }
}

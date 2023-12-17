package com.spotify.app.follower;

import com.spotify.app.controller.FollowerController;
import com.spotify.app.dto.response.UserNoAssociationResponse;
import com.spotify.app.enums.Gender;
import com.spotify.app.service.FollowerService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = FollowerController.class,
        excludeAutoConfiguration = {
                UserDetailsServiceAutoConfiguration.class, SecurityAutoConfiguration.class
        })
public class FollowerControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FollowerService followerService;

    private UserNoAssociationResponse user1 ;
    private UserNoAssociationResponse user2 ;
    private UserNoAssociationResponse user3 ;


    private String prefixUrl = "/api/v1/follower";

    @BeforeEach
    public void setUp () {
        user1 = new UserNoAssociationResponse(1L, "firstName1", "lastName1", "firstName1 lastName1", "user1@gmail.com",Gender.MALE,"imagePath1.png",true );
        user2 = new UserNoAssociationResponse(2L, "firstName2", "lastName1", "firstName2 lastName1", "user2@gmail.com",Gender.MALE,"imagePath2.png",true );
        user3 = new UserNoAssociationResponse(3L, "firstName3", "lastName1", "firstName3 lastName1", "user3@gmail.com",Gender.MALE,"imagePath3.png",true );
    }

    // get following list by userId
    @Test
    public void canGetFollowingListByUserId () throws Exception {
        // given
        List<UserNoAssociationResponse> userNoAssociationResponseList = List.of(user2, user3);
        Long wantedId = 1L;
        // when
        when(followerService.findAllFollowingsByUserId(wantedId)).thenReturn(userNoAssociationResponseList);
        // then
        String url = prefixUrl.concat(String.format("/%d/followings", wantedId));
        this.mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(2)));
    }
    // follow user
    @Test
    public void canFollowAnUser () throws Exception {
        // given
        List<UserNoAssociationResponse> userNoAssociationResponseList = List.of(user2);
        Long currentUserId = 1L;
        Long targetUserId = 2L;

        // when
        when(followerService.findAllFollowingsByUserId(currentUserId)).thenReturn(userNoAssociationResponseList);
        // then
        String url = prefixUrl.concat(String.format("/%d/follow/%d", currentUserId, targetUserId)) ;
        this.mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", Matchers.is(2)));
    }

    // unfollow user
    @Test
    public void canUnfollowUser () throws Exception {
        // given
        List<UserNoAssociationResponse> userNoAssociationResponseList = Collections.emptyList();
        Long currentUserId = 1L;
        Long targetUserId = 2L;

        // when
        when(followerService.findAllFollowingsByUserId(currentUserId)).thenReturn(userNoAssociationResponseList);
        // then
        String url = prefixUrl.concat(String.format("/%d/cancel/%d", currentUserId, targetUserId)) ;
        this.mockMvc.perform(delete(url))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(0)));
    }

}

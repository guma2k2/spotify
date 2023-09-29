package com.spotify.app.service;

import com.spotify.app.dto.response.UserResponseNoAssociation;
import com.spotify.app.mapper.UserNoAssMapper;
import com.spotify.app.model.Follower;
import com.spotify.app.model.User;
import com.spotify.app.repository.FollowerRepository;
import com.spotify.app.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class FollowerService {

    private final FollowerRepository followerRepository;

    private final UserService userService;

    private final UserNoAssMapper userNoAssMapper;

    @Transactional
    public void addFollowing(Long I_id, Long U_id ) {
//        log.info(String.valueOf(U_id));
//        log.info(String.valueOf(I_id));
        User I = userService.get(I_id);
        User U = userService.get(U_id);
        Follower follower = Follower
                .builder()
                .followingUser(I)
                .followedUser(U)
                .build();
        Follower saved = followerRepository.save(follower);
//        log.info(String.valueOf(saved));
    }

    @Transactional
    public void cancelFollowing(Long I_id, Long U_id ) {
        User I = userService.get(I_id);
        User U = userService.get(U_id);
        followerRepository.unfollowing(I,U);
    }

    public List<UserResponseNoAssociation> findAllFollowingsByUserId(Long userId) {

        List<Follower> followers = followerRepository.findFollowingListByUseId(userId);

        List<UserResponseNoAssociation> followings = followers.
                stream().
                map(follower -> userNoAssMapper.userToUserDTO(follower.getFollowedUser())).
                toList();

        return followings;
    }
}

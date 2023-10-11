package com.spotify.app.service;

import com.spotify.app.dto.response.UserNoAssociationResponse;
import com.spotify.app.exception.ResourceNotFoundException;
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

    private final UserRepository userRepository;

    private final UserNoAssMapper userNoAssMapper;

    @Transactional
    public void addFollowing(Long I_id, Long U_id ) {
        User I = getUserByUserId(I_id);
        User U = getUserByUserId(U_id);
        Follower follower = Follower
                .builder()
                .followingUser(I)
                .followedUser(U)
                .build();
        followerRepository.save(follower);
    }

    @Transactional
    public void cancelFollowing(Long I_id, Long U_id ) {
        User I = getUserByUserId(I_id);
        User U = getUserByUserId(U_id);
        followerRepository.unfollowing(I,U);
    }

    public List<UserNoAssociationResponse> findAllFollowingsByUserId(Long userId) {
        // get all followed_id by [following_id <- (userId)]
        List<Follower> followers = findFollowingListByUseId(userId);

        // convert to UserNoAssociationResponses (list of user with no associations)
        List<UserNoAssociationResponse> followings = getFollowingsInFollowerTable(followers);

        return followings;
    }

    private List<Follower> findFollowingListByUseId(Long userId) {
        return followerRepository.findFollowingListByUseId(userId);
    }

    private List<UserNoAssociationResponse> getFollowingsInFollowerTable(List<Follower> followers) {
        return followers.stream().
                map(follower -> userNoAssMapper.userToUserDTO(follower.getFollowedUser())).
                toList();
    }

    public User getUserByUserId(Long userId) {
        return userRepository.
                findById(userId).
                orElseThrow(() -> new ResourceNotFoundException(String.format("user with id: %d not found", userId)));
    }

    public boolean checkCurrentUserFollowedTargetUser(Long currentUserId, Long targetUserId) {
        return followerRepository.findByFollowingIdAndFollowedId(currentUserId,targetUserId).isPresent();
    }

}

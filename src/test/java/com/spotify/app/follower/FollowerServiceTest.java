package com.spotify.app.follower;

import com.spotify.app.mapper.UserNoAssMapper;
import com.spotify.app.model.Follower;
import com.spotify.app.model.User;
import com.spotify.app.repository.FollowerRepository;
import com.spotify.app.repository.UserRepository;
import com.spotify.app.service.FollowerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class FollowerServiceTest {

    private FollowerService underTest;


    @Mock
    private  FollowerRepository followerRepository;

    @Mock
    private  UserRepository userRepository;

    @Mock
    private  UserNoAssMapper userNoAssMapper;


    @BeforeEach
    void setUp () {
        underTest = new FollowerService(followerRepository, userRepository, userNoAssMapper);
    }

    // can follow
    @Test
    public void canFollow () {
        // given
        Long currentUserId = 1L;
        Long targetUserId = 2L;

        Mockito.when(userRepository.findById(currentUserId)).thenReturn(Optional.of(new User(currentUserId))); // Assuming User is your entity class
        Mockito.when(userRepository.findById(targetUserId)).thenReturn(Optional.of(new User(targetUserId)));

        Mockito.when(followerRepository.findByFollowingIdAndFollowedId(currentUserId, targetUserId)).thenReturn(Optional.empty());

        // Act
        underTest.addFollowing(currentUserId, targetUserId);

        // Assert
        // You can add assertions based on the behavior of your service method or check the database state
        Mockito.verify(followerRepository, Mockito.times(1)).saveAndFlush(Mockito.any());
    }
    // can unfollow

    @Test
    public void canUnfollow () {
        // given
        Long currentUserId = 1L;
        Long targetUserId = 2L;
        User i = new User(currentUserId);
        User u = new User(targetUserId);
        Follower follower = Follower
                .builder()
                .followingUser(i)
                .followedUser(u)
                .build();

        // when
        Mockito.when(followerRepository.findByFollowingIdAndFollowedId(currentUserId, targetUserId)).thenReturn(Optional.of(follower));
        Mockito.when(userRepository.findById(currentUserId)).thenReturn(Optional.of(i)); // Assuming User is your entity class
        Mockito.when(userRepository.findById(targetUserId)).thenReturn(Optional.of(u));


        // then
        underTest.unfollowing(currentUserId, targetUserId);
        Mockito.verify(followerRepository, Mockito.times(1)).unfollowing(Mockito.any(), Mockito.any());
    }
    // cannot follow
    @Test
    public void cannotFollowWithCheckCurrentUserFollowedTargetUserIsFalse () {
        // given
        Long currentUserId = 1L;
        Long targetUserId = 2L;
        User i = new User(currentUserId);
        User u = new User(targetUserId);
        Follower follower = Follower
                .builder()
                .followingUser(i)
                .followedUser(u)
                .build();

        // when
        Mockito.when(followerRepository.findByFollowingIdAndFollowedId(currentUserId, targetUserId)).thenReturn(Optional.of(follower));

        // then
        underTest.addFollowing(currentUserId, targetUserId);
        Mockito.verify(followerRepository, Mockito.never()).saveAndFlush(Mockito.any());
    }
    @Test
    public void cannotUnfollowWithCheckCurrentUserFollowedTargetUserIsFalse () {
        // given
        Long currentUserId = 1L;
        Long targetUserId = 2L;

        // when
        Mockito.when(followerRepository.findByFollowingIdAndFollowedId(currentUserId, targetUserId)).thenReturn(Optional.empty());

        // then
        underTest.unfollowing(currentUserId, targetUserId);
        Mockito.verify(followerRepository, Mockito.never()).unfollowing(Mockito.any(), Mockito.any());
    }

    // can get all followings
    @Test
    public void canGetAllFollowings () {
        // given
        Long currentUserId = 1L;
        Long targetUserId = 2L;
        User i = new User(currentUserId);
        User u = new User(targetUserId);
        Follower follower = Follower
                .builder()
                .followingUser(i)
                .followedUser(u)
                .build();
        List<Follower> expect = List.of(follower);

        // when
        Mockito.when(followerRepository.findFollowingListByUserId(currentUserId)).thenReturn(expect);

        // then
        var actual = underTest.findFollowingListByUseId(currentUserId);
        assertThat(actual).isEqualTo(expect);
    }
}

//package com.spotify.app.follower;
//import com.spotify.app.AbstractTestcontainers;
//import com.spotify.app.TestConfig;
//import com.spotify.app.model.Follower;
//import com.spotify.app.repository.FollowerRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.context.annotation.Import;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Import({TestConfig.class})
//public class FollowerRepositoryTest extends AbstractTestcontainers {
//
//    @Autowired
//    private FollowerRepository underTest ;
//
//    @Test
//    public void canListFollowingListByUserId () {
//        // given
//        Long userId = 1L;
//
//        // when
//        List<Follower> followerList = underTest.findFollowingListByUserId(userId);
//
//        // then
//        assertThat(followerList.size()).isGreaterThan(0);
//    }
//}

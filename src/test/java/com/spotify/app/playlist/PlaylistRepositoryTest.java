//package com.spotify.app.playlist;
//
//
//import com.spotify.app.AbstractTestcontainers;
//import com.spotify.app.TestConfig;
//import com.spotify.app.repository.PlaylistRepository;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.context.annotation.Import;
//
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Import({TestConfig.class})
//public class PlaylistRepositoryTest extends AbstractTestcontainers {
//
//
//    @Autowired
//    private PlaylistRepository underTest ;
//
//    @Test
//    public void shouldReturnCreatedUser_WhenFindByIdReturnPlaylistUsers() {
//        // given
//        Long playlistId = 1L;
//
//        // when
//        var playlist = underTest.findByIdReturnPlaylistUsers(playlistId);
//
//        // then
//        assertThat(playlist).isPresent();
//        assertThat(playlist.get().getPlaylistUserList().size()).isGreaterThan(0);
//    }
//
//}

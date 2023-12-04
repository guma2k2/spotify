//package com.spotify.app.song;
//
//import com.spotify.app.AbstractTestcontainers;
//import com.spotify.app.TestConfig;
//import com.spotify.app.model.Song;
//import com.spotify.app.repository.SongRepository;
//import jakarta.transaction.Transactional;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.context.annotation.Import;
//
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Import({TestConfig.class})
//@Slf4j
//public class SongRepositoryTest extends AbstractTestcontainers {
//
//
//    @Autowired
//    private SongRepository underTest ;
//
//    @Test
//    public void canFindByIdReturnUserAlbumReview () {
//        // given
//        Long songId = 1L;
//
//        // when
//        var actual = underTest.findByIdReturnUsersAlbumsReviews(songId);
//        log.info(String.valueOf(actual));
//
//        // then
//        assert (actual.isPresent());
//    }
//}

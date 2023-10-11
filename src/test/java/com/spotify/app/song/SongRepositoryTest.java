package com.spotify.app.song;

import com.spotify.app.AbstractTestcontainers;
import com.spotify.app.TestConfig;
import com.spotify.app.enums.Gender;
import com.spotify.app.enums.Genre;
import com.spotify.app.model.Album;
import com.spotify.app.model.Song;
import com.spotify.app.model.User;
import com.spotify.app.repository.SongRepository;
import com.spotify.app.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({TestConfig.class})
public class SongRepositoryTest extends AbstractTestcontainers {


    @Autowired
    private SongRepository underTest ;

    @Autowired
    private UserRepository userRepository;


    @Test
    public void shouldReturnSongsUsers__whenFindById() {
        // given
        User artist1 = User.builder().firstName("f").lastName("s").email("aa").gender(Gender.MALE).build();

        Song expected = Song.builder()
                .id(100L)
                .name("song1")
                .genre(Genre.POP)
                .lyric("lyric")
                .build();
        Album album = Album.builder().user(artist1).name("album1").id(100L).build();
        album.addSong(expected);
        artist1.addSong(expected);
        userRepository.saveAndFlush(artist1);
        // when
        Optional<Song> actual = underTest.findByIdReturnUsersAlbums(100L);

        // then
        assert (actual.isPresent());

    }
}

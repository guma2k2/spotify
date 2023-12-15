package com.spotify.app.album;


import com.spotify.app.AbstractTestcontainers;
import com.spotify.app.TestConfig;
import com.spotify.app.repository.AlbumRepository;
import com.spotify.app.repository.SongRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({TestConfig.class})
public class AlbumRepositoryTest extends AbstractTestcontainers {


    @Autowired
    private AlbumRepository underTest;

    @Autowired
    private SongRepository songRepository;
    @Test
    public void canFindById() {
        // given
        Long albumId = 7L;

        var album = underTest.findById(albumId);

        assertThat(album).isPresent();

    }

    @Test
    public void canFindByIdAndReturnItsSong() {
        Long albumId = 7L;
        Long songId = 1L;
        var album = underTest.findById(albumId).get();
        var song = songRepository.findById(songId).get();
        album.addSong(song);

        underTest.saveAndFlush(album);

        var expected = underTest.findByIdReturnSongs(albumId).get();

        boolean check = expected.getAlbumSongList().stream().anyMatch(albumSong -> albumSong.getSong().getId().equals(songId));
        assertThat(album).isNotNull();
        assertThat(song).isNotNull();
        assertThat(expected).isNotNull();
        assertThat(check).isTrue();
    }
}

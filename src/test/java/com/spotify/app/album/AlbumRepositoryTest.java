package com.spotify.app.album;


import com.spotify.app.SpotifyApplication;
import com.spotify.app.TestConfig;
import com.spotify.app.enums.Genre;
import com.spotify.app.model.Album;
import com.spotify.app.model.Song;
import com.spotify.app.model.User;
import com.spotify.app.repository.AlbumRepository;
import com.spotify.app.repository.SongRepository;
import com.spotify.app.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({TestConfig.class})
public class AlbumRepositoryTest {


    private final AlbumRepository albumRepository ;
    private final SongRepository songRepository;

    @Autowired
    public AlbumRepositoryTest(AlbumRepository albumRepository, SongRepository songRepository) {
        this.albumRepository = albumRepository;
        this.songRepository = songRepository;
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void createAlbum_thenReturn_statusSuccess() {
        Song song1 = Song.builder()
                .releaseDate(LocalDateTime.now())
                .name("song1")
                .duration(232)
                .genre(Genre.POP)
                .build();

        Song song2 = Song.builder()
                .releaseDate(LocalDateTime.now())
                .name("song2")
                .duration(232)
                .genre(Genre.POP)
                .build();

//        Song savedSong2 = this.songRepository.save(song2);
//        Song savedSong1 = this.songRepository.save(song1);

        this.songRepository.saveAndFlush(song1);
        this.songRepository.saveAndFlush(song2);
        Album album = Album.builder()
                .name("thuan")
                .releaseDate(LocalDateTime.now())
                .build();
        Album savedAlbum = this.albumRepository.saveAndFlush(album);
        album.addSong(song1);
        album.addSong(song2);

        assert(savedAlbum.getAlbumSongList().size() > 0);

        /*assert(savedAlbum.getName()).equals(album.getName());*/
    }
}

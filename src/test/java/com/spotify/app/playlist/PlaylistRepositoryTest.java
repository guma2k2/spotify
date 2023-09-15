package com.spotify.app.playlist;


import com.spotify.app.TestConfig;
import com.spotify.app.enums.Genre;
import com.spotify.app.model.Album;
import com.spotify.app.model.Category;
import com.spotify.app.model.Playlist;
import com.spotify.app.model.Song;
import com.spotify.app.repository.AlbumRepository;
import com.spotify.app.repository.CategoryRepository;
import com.spotify.app.repository.PlaylistRepository;
import com.spotify.app.repository.SongRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({TestConfig.class})
public class PlaylistRepositoryTest {


    private final PlaylistRepository playlistRepository ;

    private final CategoryRepository categoryRepository ;

    private final EntityManager entityManager ;

    @Autowired
    public PlaylistRepositoryTest(PlaylistRepository playlistRepository, EntityManager entityManager, CategoryRepository categoryRepository) {
        this.playlistRepository = playlistRepository;
        this.entityManager = entityManager;
        this.categoryRepository = categoryRepository;
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
        entityManager.persist(song1);
        entityManager.persist(song2);
        Playlist playlist = Playlist.builder()
                .description("desc")
                .name("playlist1")
                .build();
        this.entityManager.persist(playlist);
        playlist.addSong(song1);
        playlist.addSong(song2);
        this.entityManager.merge(playlist);
        assert(playlist.getPlaylistSongList().size() > 0);

        /*assert(savedAlbum.getName()).equals(album.getName());*/
    }


    @Test
    @Transactional
    @Rollback(value = false)
    public void createAlbum() {
        Song song1 = new Song(1l);
        Song song2 = new Song(2l);
        Song song3 = new Song(3l);
        Song song4 = new Song(4l);
        Song song5 = new Song(5l);
        Song song6 = new Song(6l);
        Song song7 = new Song(7l);
        Song song8 = new Song(8l);

        //         Init category
        Category parent = Category.builder()
                .title("parent1")
                .image("image")
                .build();

        Category child1 = Category.builder()
                .title("child1")
                .image("image")
                .categoryParent(parent)
                .build();

        Category child2 = Category.builder()
                .title("child2")
                .image("image")
                .categoryParent(parent)
                .build();
        parent.addChild(child2);
        parent.addChild(child1);
        categoryRepository.saveAndFlush(parent);


        Playlist playlist1Child1 = Playlist.builder()
                .name("playlist1Child1")
                .description("playlist_1_Child_1_Desc")
                .build();
        Playlist playlist2Child1 = Playlist.builder()
                .name("playlist2Child1")
                .description("playlist_2_Child_1_Desc")
                .build();

        Playlist playlist1Child2 = Playlist.builder()
                .name("playlist1Child2")
                .description("playlist_1_Child_2_Desc")
                .build();
        Playlist playlist2Child2 = Playlist.builder()
                .name("playlist2Child2")
                .description("playlist_2_Child_2_Desc")
                .build();
        playlistRepository.saveAllAndFlush(List.of(playlist1Child1,playlist1Child2,playlist2Child1,playlist2Child2));
        child1.addPlaylist(playlist1Child1);
        child1.addPlaylist(playlist2Child1);
        child2.addPlaylist(playlist1Child2);
        child2.addPlaylist(playlist2Child2);
        categoryRepository.saveAll(List.of(child1,child2));

        playlist1Child1.addSong(song1);
        playlist1Child1.addSong(song2);
        playlist1Child2.addSong(song3);
        playlist1Child2.addSong(song4);
        playlist2Child1.addSong(song5);
        playlist2Child1.addSong(song6);
        playlist2Child2.addSong(song7);
        playlist2Child2.addSong(song8);
        playlistRepository.saveAllAndFlush(List.of(playlist1Child1,playlist1Child2,playlist2Child1,playlist2Child2));

    }
}

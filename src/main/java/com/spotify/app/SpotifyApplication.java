package com.spotify.app;

import com.spotify.app.enums.Gender;
import com.spotify.app.enums.Genre;
import com.spotify.app.model.*;
import com.spotify.app.repository.*;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class SpotifyApplication {

    private final EntityManager entityManager ;
    private final PasswordEncoder passwordEncoder ;

    private final RoleRepository roleRepository ;

    private final CategoryRepository categoryRepository ;

    private final PlaylistRepository playlistRepository;

    private final AlbumRepository albumRepository;

    private final SongRepository songRepository;
    public static void main(String[] args) {
        SpringApplication.run(SpotifyApplication.class, args) ;
    }

//    @PostConstruct
//    @Transactional
//    public void init() {
//        User user1 = User.builder()
//                .firstName("phi")
//                .lastName("phi")
//                .email("phiphi@gmail.com")
//                .password(passwordEncoder.encode("thuan@gmail.com"))
//                .gender(Gender.MALE)
//                .build();
//
//        User user2 = User.builder()
//                .firstName("thuan")
//                .lastName("thuan")
//                .email("thuan@gmail.com")
//                .password(passwordEncoder.encode("thuan@gmail.com"))
//                .gender(Gender.MALE)
//                .build();
//
//        User user3 = User.builder()
//                .firstName("vinh")
//                .lastName("vinh")
//                .email("vinh@gmail.com")
//                .password(passwordEncoder.encode("thuan@gmail.com"))
//                .gender(Gender.MALE)
//                .build();
//
//        User user4 = User.builder()
//                .firstName("vinhpro")
//                .lastName("vinh")
//                .email("vinhh@gmail.com")
//                .password(passwordEncoder.encode("thuan@gmail.com"))
//                .gender(Gender.MALE)
//                .build();
//        User user5 = User.builder()
//                .firstName("phii")
//                .lastName("phii")
//                .email("phii@gmail.com")
//                .password(passwordEncoder.encode("thuan@gmail.com"))
//                .gender(Gender.MALE)
//                .build();
//
//
//        Role roleUser = Role.builder()
//                .name("ROLE_CUSTOMER")
//                .build();
//
//        Role roleArtist = Role.builder()
//                .name("ROLE_ARTIST")
//                .build();
//
//        Song song1 = Song.builder()
//                .lyric("lyric")
//                .name("song1")
//                .duration(281)
//                .releaseDate(LocalDateTime.now())
//                .genre(Genre.POP)
//                .build();
//        Song song2 = Song.builder()
//                .lyric("lyric")
//                .name("song2")
//                .duration(282)
//                .releaseDate(LocalDateTime.now())
//                .genre(Genre.CLASSICAL)
//                .build();
//        Song song3 = Song.builder()
//                .lyric("lyric")
//                .name("song3")
//                .duration(282)
//                .releaseDate(LocalDateTime.now())
//                .genre(Genre.Hip_Hop)
//                .build();
//        Song song4 = Song.builder()
//                .lyric("lyric")
//                .name("song4")
//                .duration(282)
//                .releaseDate(LocalDateTime.now())
//                .genre(Genre.Rock)
//                .build();
//        Song song5 = Song.builder()
//                .lyric("lyric")
//                .name("song5")
//                .duration(282)
//                .releaseDate(LocalDateTime.now())
//                .genre(Genre.CLASSICAL)
//                .build();
//        Song song6 = Song.builder()
//                .lyric("lyric")
//                .name("song6")
//                .duration(282)
//                .releaseDate(LocalDateTime.now())
//                .genre(Genre.POP)
//                .build();
//        Song song7 = Song.builder()
//                .lyric("lyric")
//                .name("song7")
//                .duration(282)
//                .releaseDate(LocalDateTime.now())
//                .genre(Genre.Hip_Hop)
//                .build();
//        Song song8 = Song.builder()
//                .lyric("lyric")
//                .name("song8")
//                .duration(282)
//                .releaseDate(LocalDateTime.now())
//                .genre(Genre.Rock)
//                .build();
//        Song song9 = Song.builder()
//                .lyric("lyric")
//                .name("song9")
//                .duration(282)
//                .releaseDate(LocalDateTime.now())
//                .genre(Genre.CLASSICAL)
//                .build();
//        Song song10 = Song.builder()
//                .lyric("lyric")
//                .name("song10")
//                .duration(282)
//                .releaseDate(LocalDateTime.now())
//                .genre(Genre.POP)
//                .build();
//
//        roleArtist.addUser(user1);
//        roleArtist.addUser(user4);
//        roleArtist.addUser(user5);
//        roleUser.addUser(user3);
//        roleArtist.addUser(user2);
//        user3.addSong(song10);
//        user2.addSong(song1);
//        user2.addSong(song2);
//        user1.addSong(song3);
//        user1.addSong(song4);
//        user1.addSong(song5);
//        user4.addSong(song6);
//        user4.addSong(song7);
//        user5.addSong(song8);
//        user5.addSong(song9);
//        roleRepository.saveAll(List.of(roleUser,roleArtist));
//    }


        ////////////////////////////////////////////////////////////////////////////////////////////////////



//    @PostConstruct
//    @Transactional
//    public void init2() {
//        Song song1 = songRepository.findById(1l).get();
//        Song song2 = songRepository.findById(2l).get();
//        Song song3 = songRepository.findById(3l).get();
//        Song song4 = songRepository.findById(4l).get();
//        Song song5 = songRepository.findById(5l).get();
//        Song song6 = songRepository.findById(6l).get();
//        Song song7 = songRepository.findById(7l).get();
//        Song song8 = songRepository.findById(8l).get();
//        Song song9 = songRepository.findById(9l).get();
//        Song song10 = songRepository.findById(10l).get();
//
//        Album album1 = Album.builder()
//                .releaseDate(LocalDateTime.now())
//                .name("album1")
//                .build();
//
//        Album album2 = Album.builder()
//                .releaseDate(LocalDateTime.now())
//                .name("album2")
//                .build();
//
//        Album album4 = Album.builder()
//                .releaseDate(LocalDateTime.now())
//                .name("album4")
//                .build();
//
//        Album album5 = Album.builder()
//                .releaseDate(LocalDateTime.now())
//                .name("album5")
//                .build();
//
//        albumRepository.saveAndFlush(album1);
//        albumRepository.saveAndFlush(album2);
//        albumRepository.saveAndFlush(album4);
//        albumRepository.saveAndFlush(album5);
//
//
//        album1.addSong(song1);
//        album1.addSong(song2);
//        album1.addSong(song3);
//
//        album2.addSong(song4);
//        album2.addSong(song5);
//        album2.addSong(song6);
//
//        album4.addSong(song7);
//        album4.addSong(song9);
//
//        album5.addSong(song10);
//        album5.addSong(song8);
//
//        albumRepository.saveAndFlush(album1);
//        albumRepository.saveAndFlush(album2);
//        albumRepository.saveAndFlush(album4);
//        albumRepository.saveAndFlush(album5);
//    }

//    @PostConstruct
//    @Transactional
//    public void init3() {
//        Song song1 = songRepository.findById(1l).get();
//        Song song2 = songRepository.findById(2l).get();
//        Song song3 = songRepository.findById(3l).get();
//        Song song4 = songRepository.findById(4l).get();
//        Song song5 = songRepository.findById(5l).get();
//        Song song6 = songRepository.findById(6l).get();
//        Song song7 = songRepository.findById(7l).get();
//        Song song8 = songRepository.findById(8l).get();
//
//        //         Init category
//        Category parent = Category.builder()
//                .title("parent1")
//                .image("image")
//                .build();
//
//        Category child1 = Category.builder()
//                .title("child1")
//                .image("image")
//                .categoryParent(parent)
//                .build();
//
//        Category child2 = Category.builder()
//                .title("child2")
//                .image("image")
//                .categoryParent(parent)
//                .build();
//        parent.addChild(child2);
//        parent.addChild(child1);
//        categoryRepository.saveAndFlush(parent);
//
//
//        Playlist playlist1Child1 = Playlist.builder()
//                .name("playlist1Child1")
//                .description("playlist_1_Child_1_Desc")
//                .build();
//        Playlist playlist2Child1 = Playlist.builder()
//                .name("playlist2Child1")
//                .description("playlist_2_Child_1_Desc")
//                .build();
//
//        Playlist playlist1Child2 = Playlist.builder()
//                .name("playlist1Child2")
//                .description("playlist_1_Child_2_Desc")
//                .build();
//        Playlist playlist2Child2 = Playlist.builder()
//                .name("playlist2Child2")
//                .description("playlist_2_Child_2_Desc")
//                .build();
//        playlistRepository.saveAllAndFlush(List.of(playlist1Child1,playlist1Child2,playlist2Child1,playlist2Child2));
//        child1.addPlaylist(playlist1Child1);
//        child1.addPlaylist(playlist2Child1);
//        child2.addPlaylist(playlist1Child2);
//        child2.addPlaylist(playlist2Child2);
//        categoryRepository.saveAll(List.of(child1,child2));
//
//        playlist1Child1.addSong(song1);
//        playlist1Child1.addSong(song2);
//        playlist1Child2.addSong(song3);
//        playlist1Child2.addSong(song4);
//        playlist2Child1.addSong(song5);
//        playlist2Child1.addSong(song6);
//        playlist2Child2.addSong(song7);
//        playlist2Child2.addSong(song8);
//        playlistRepository.saveAllAndFlush(List.of(playlist1Child1,playlist1Child2,playlist2Child1,playlist2Child2));
//    }

}

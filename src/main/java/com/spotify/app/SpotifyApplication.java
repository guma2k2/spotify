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
//        User user6 = User.builder()
//                .firstName("phiiii")
//                .lastName("phiiiii")
//                .email("phiphi1@gmail.com")
//                .password(passwordEncoder.encode("thuan@gmail.com"))
//                .gender(Gender.MALE)
//                .build();
//
//        User user7 = User.builder()
//                .firstName("thuann")
//                .lastName("thuann")
//                .email("thuan1@gmail.com")
//                .password(passwordEncoder.encode("thuan@gmail.com"))
//                .gender(Gender.MALE)
//                .build();
//
//        User user8 = User.builder()
//                .firstName("vinhh")
//                .lastName("vinhh")
//                .email("vinh1@gmail.com")
//                .password(passwordEncoder.encode("thuan@gmail.com"))
//                .gender(Gender.MALE)
//                .build();
//
//        User user9 = User.builder()
//                .firstName("avinhpro")
//                .lastName("vinh")
//                .email("vinhh2@gmail.com")
//                .password(passwordEncoder.encode("thuan@gmail.com"))
//                .gender(Gender.MALE)
//                .build();
//        User user10 = User.builder()
//                .firstName("phiiiiii")
//                .lastName("phii")
//                .email("phii2@gmail.com")
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
//        Song song11 = Song.builder()
//                .lyric("lyric")
//                .name("song11")
//                .duration(281)
//                .releaseDate(LocalDateTime.now())
//                .genre(Genre.POP)
//                .build();
//        Song song12 = Song.builder()
//                .lyric("lyric")
//                .name("song12")
//                .duration(282)
//                .releaseDate(LocalDateTime.now())
//                .genre(Genre.CLASSICAL)
//                .build();
//        Song song13 = Song.builder()
//                .lyric("lyric")
//                .name("song13")
//                .duration(282)
//                .releaseDate(LocalDateTime.now())
//                .genre(Genre.Hip_Hop)
//                .build();
//        Song song14 = Song.builder()
//                .lyric("lyric")
//                .name("song14")
//                .duration(282)
//                .releaseDate(LocalDateTime.now())
//                .genre(Genre.Rock)
//                .build();
//        Song song15 = Song.builder()
//                .lyric("lyric")
//                .name("song15")
//                .duration(282)
//                .releaseDate(LocalDateTime.now())
//                .genre(Genre.CLASSICAL)
//                .build();
//        Song song16 = Song.builder()
//                .lyric("lyric")
//                .name("song16")
//                .duration(282)
//                .releaseDate(LocalDateTime.now())
//                .genre(Genre.POP)
//                .build();
//        Song song17 = Song.builder()
//                .lyric("lyric")
//                .name("song17")
//                .duration(282)
//                .releaseDate(LocalDateTime.now())
//                .genre(Genre.Hip_Hop)
//                .build();
//        Song song18 = Song.builder()
//                .lyric("lyric")
//                .name("song18")
//                .duration(282)
//                .releaseDate(LocalDateTime.now())
//                .genre(Genre.Rock)
//                .build();
//        Song song19 = Song.builder()
//                .lyric("lyric")
//                .name("song19")
//                .duration(282)
//                .releaseDate(LocalDateTime.now())
//                .genre(Genre.CLASSICAL)
//                .build();
//        Song song20 = Song.builder()
//                .lyric("lyric")
//                .name("song20")
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
//
//        roleArtist.addUser(user6);
//        roleArtist.addUser(user7);
//        roleArtist.addUser(user8);
//        roleArtist.addUser(user9);
//        roleArtist.addUser(user10);
//
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
//
//        user6.addSong(song11);
//        user6.addSong(song12);
//        user7.addSong(song13);
//        user7.addSong(song14);
//        user8.addSong(song15);
//        user9.addSong(song16);
//        user10.addSong(song17);
//        user8.addSong(song18);
//        user9.addSong(song19);
//        user10.addSong(song20);
//
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
//        Song song11 = songRepository.findById(11l).get();
//        Song song12 = songRepository.findById(12l).get();
//        Song song13 = songRepository.findById(13l).get();
//        Song song14 = songRepository.findById(14l).get();
//        Song song15 = songRepository.findById(15l).get();
//        Song song16 = songRepository.findById(16l).get();
//        Song song17 = songRepository.findById(17l).get();
//        Song song18 = songRepository.findById(18l).get();
//        Song song19 = songRepository.findById(19l).get();
//        Song song20 = songRepository.findById(20l).get();
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
//        Album album6 = Album.builder()
//                .releaseDate(LocalDateTime.now())
//                .name("album6")
//                .build();
//
//        Album album7 = Album.builder()
//                .releaseDate(LocalDateTime.now())
//                .name("album7")
//                .build();
//
//        Album album8 = Album.builder()
//                .releaseDate(LocalDateTime.now())
//                .name("album8")
//                .build();
//
//        Album album9 = Album.builder()
//                .releaseDate(LocalDateTime.now())
//                .name("album9")
//                .build();
//        Album album10 = Album.builder()
//                .releaseDate(LocalDateTime.now())
//                .name("album10")
//                .build();
//
//        albumRepository.saveAndFlush(album1);
//        albumRepository.saveAndFlush(album2);
//        albumRepository.saveAndFlush(album4);
//        albumRepository.saveAndFlush(album5);
//        albumRepository.saveAndFlush(album6);
//        albumRepository.saveAndFlush(album7);
//        albumRepository.saveAndFlush(album8);
//        albumRepository.saveAndFlush(album9);
//        albumRepository.saveAndFlush(album10);
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
//        album6.addSong(song11);
//        album6.addSong(song12);
//
//        album7.addSong(song13);
//        album7.addSong(song14);
//
//        album8.addSong(song15);
//        album8.addSong(song16);
//
//        album9.addSong(song17);
//        album9.addSong(song18);
//
//        album10.addSong(song19);
//        album10.addSong(song20);
//
//
//        albumRepository.saveAndFlush(album1);
//        albumRepository.saveAndFlush(album2);
//        albumRepository.saveAndFlush(album4);
//        albumRepository.saveAndFlush(album5);
//
//        albumRepository.saveAndFlush(album6);
//        albumRepository.saveAndFlush(album7);
//        albumRepository.saveAndFlush(album8);
//        albumRepository.saveAndFlush(album9);
//        albumRepository.saveAndFlush(album10);
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
//
//        //          GROUP PARENT CATEGORY
//        Category parent = Category.builder()
//                .title("HOME")
//                .build();
//
//        Category parent1 = Category.builder()
//                .title("PodCasts")
//                .build();
//
//        Category parent3 = Category.builder()
//                .title("BẢNG XẾP HẠNG")
//                .build();
//
//        Category parent4 = Category.builder()
//                .title("KHÁM PHÁ")
//                .build();
//        Category parent5 = Category.builder()
//                .title("MỚI PHÁT HÀNH")
//                .build();
//
//        Category parent6 = Category.builder()
//                .title("NHẠC VIỆT")
//                .build();
//
//        Category parent7 = Category.builder()
//                .title("POP")
//                .build();
//
//        Category parent8 = Category.builder()
//                .title("KPOP")
//                .build();
//
//        Category parent9 = Category.builder()
//                .title("TÂM TRẠNG")
//                .build();
//
//        Category parent10 = Category.builder()
//                .title("KHÔNG GIAN LÃNG MẠN")
//                .build();
//
//        Category parent11 = Category.builder()
//                .title("SỨC KHỎE")
//                .build();
//
//        Category parent12 = Category.builder()
//                .title("ROCK")
//                .build();
//
//        Category parent13 = Category.builder()
//                .title("LEAGUE OF LEGEND")
//                .build();
//
//        Category parent14 = Category.builder()
//                .title("TRÊN XE")
//                .build();
//
//        Category parent15 = Category.builder()
//                .title("THỊNH HÀNH")
//                .build();
//
//        Category parent16 = Category.builder()
//                .title("TẬP TRUNG")
//                .build();
//
//        Category parent17 = Category.builder()
//                .title("MÙA HÈ")
//                .build();
//
//        Category parent18 = Category.builder()
//                .title("CHƠI GAME")
//                .build();
//
//        Category parent19 = Category.builder()
//                .title("CỔ ĐIỂN")
//                .build();
//
//        Category parent20 = Category.builder()
//                .title("DU LỊCH")
//                .build();
//        Category parent21 = Category.builder()
//                .title("ANIME")
//                .build();
//        Category parent22 = Category.builder()
//                .title("NHẠC KHÔNG LỜI")
//                .build();
//
//        Category parent23 = Category.builder()
//                .title("HIP HOP")
//                .build();
//
//        //////////////////////
//
//
//        Category child37 = Category.builder()
//                .title("Danh sách phát MÙA HÈ phổ biến")
//                .categoryParent(parent17)
//                .build();
//        parent17.addChild(child37);
//
//        Category child36 = Category.builder()
//                .title("Danh sách phát trò chơi phổ biến vãi ")
//                .categoryParent(parent18)
//                .build();
//        parent18.addChild(child36);
//
//        Category child35 = Category.builder()
//                .title("Danh sách phát trên xe phổ biến")
//                .categoryParent(parent14)
//                .build();
//        parent14.addChild(child35);
//
//        Category child32 = Category.builder()
//                .title("Danh sách phát Anime phổ biến")
//                .categoryParent(parent21)
//                .build();
//        parent21.addChild(child32);
//
//        Category child33 = Category.builder()
//                .title("Danh sách phát du lịch phổ biến")
//                .categoryParent(parent20)
//                .build();
//        parent20.addChild(child33);
//
//        Category child34 = Category.builder()
//                .title("Danh sách phát nhạc cổ điển nổi bật")
//                .categoryParent(parent19)
//                .build();
//        parent19.addChild(child34);
//
//
//
//        Category child1 = Category.builder()
//                .title("Danh sách phát trên Spotify")
//                .categoryParent(parent)
//                .build();
//
//        Category child2 = Category.builder()
//                .title("TẬP TRUNG MẠNH")
//                .categoryParent(parent)
//                .build();
//
//        Category child3 = Category.builder()
//                .title("Podcast hàng đầu")
//                .categoryParent(parent1)
//                .build();
//
//        Category child4 = Category.builder()
//                .title("Các câu chuyện")
//                .categoryParent(parent1)
//                .build();
//
//        Category child5 = Category.builder()
//                .title("Bảng xếp hạng nổi bật")
//                .categoryParent(parent3)
//                .build();
//
//
//        Category child6 = Category.builder()
//                .title("Bảng xếp hạng bài hát hàng tuần")
//                .categoryParent(parent3)
//                .build();
//
//        Category child7 = Category.builder()
//                .title("Bản mới phát hành nhất")
//                .categoryParent(parent5)
//                .build();
//
//        Category child8 = Category.builder()
//                .title("Album và đĩa đơn mới")
//                .categoryParent(parent5)
//                .build();
//
//        Category child9 = Category.builder()
//                .title("Nghỉ ngơi")
//                .categoryParent(parent4)
//                .build();
//
//        Category child10 = Category.builder()
//                .title("Thư giãn")
//                .categoryParent(parent4)
//                .build();
//
//        Category child11 = Category.builder()
//                .title("Nhạc viện thịnh hành")
//                .categoryParent(parent6)
//                .build();
//
//        Category child12 = Category.builder()
//                .title("Diva nhạc Việt")
//                .categoryParent(parent6)
//                .build();
//
//        Category child13 = Category.builder()
//                .title("Kpop không thể thiếu")
//                .categoryParent(parent8)
//                .build();
//
//        Category child14 = Category.builder()
//                .title("Phim Hàn Quốc và nhiều nội dung khác")
//                .categoryParent(parent8)
//                .build();
//
//        Category child15 = Category.builder()
//                .title("Latin POP")
//                .categoryParent(parent7)
//                .build();
//
//        Category child16 = Category.builder()
//                .title("Chìm đắm trong thế giới Barbie")
//                .categoryParent(parent7)
//                .build();
//
//        Category child17 = Category.builder()
//                .title("Mọi giai điệu nhạc Pop")
//                .categoryParent(parent7)
//                .build();
//
//        Category child18 = Category.builder()
//                .title("Tâm trạng của bạn hôm này thế nào ?")
//                .categoryParent(parent9)
//                .build();
//
//        Category child19 = Category.builder()
//                .title("Những giai điệu u sầu nhất thế giói")
//                .categoryParent(parent9)
//                .build();
//
//        Category child20 = Category.builder()
//                .title("Nhạc trữ tình phổ biến")
//                .categoryParent(parent10)
//                .build();
//
//        Category child21 = Category.builder()
//                .title("Mùa cưới")
//                .categoryParent(parent10)
//                .build();
//
//        Category child22 = Category.builder()
//                .title("Danh sách phổ biến về sức khỏe")
//                .categoryParent(parent11)
//                .build();
//
//        Category child23 = Category.builder()
//                .title("Âm nhạc và thiên nhiên")
//                .categoryParent(parent11)
//                .build();
//
//        Category child24 = Category.builder()
//                .title("Danh sách nhạc HipHop phổ biến")
//                .categoryParent(parent23)
//                .build();
//
//        Category child25 = Category.builder()
//                .title("Chất liệu cổ điển")
//                .categoryParent(parent23)
//                .build();
//
//        Category child26 = Category.builder()
//                .title("Nhịp đập của Rock")
//                .categoryParent(parent12)
//                .build();
//
//        Category child27 = Category.builder()
//                .title("J-Rock tuần qua")
//                .categoryParent(parent12)
//                .build();
//
//        Category child28 = Category.builder()
//                .title("Thanh âm của LOL")
//                .categoryParent(parent13)
//                .build();
//
//        Category child29 = Category.builder()
//                .title("Danh sách phát trò chơi phổ biến")
//                .categoryParent(parent13)
//                .build();
//
//        Category child30 = Category.builder()
//                .title("Danh sách nhạc ru ngủ phổ biến")
//                .categoryParent(parent22)
//                .build();
//
//        Category child31 = Category.builder()
//                .title("Nhạc không lời giúp dễ ngủ")
//                .categoryParent(parent22)
//                .build();
//
//
//        parent.addChild(child1);
//        parent.addChild(child2);
//        parent1.addChild(child3);
//        parent1.addChild(child4);
//        parent3.addChild(child5);
//        parent3.addChild(child6);
//        parent5.addChild(child7);
//        parent5.addChild(child8);
//        parent4.addChild(child9);
//        parent4.addChild(child10);
//        parent6.addChild(child11);
//        parent6.addChild(child12);
//        parent8.addChild(child13);
//        parent8.addChild(child14);
//        parent7.addChild(child15);
//        parent7.addChild(child16);
//        parent7.addChild(child17);
//        parent9.addChild(child18);
//        parent9.addChild(child19);
//        parent10.addChild(child20);
//        parent10.addChild(child21);
//        parent11.addChild(child22);
//        parent11.addChild(child23);
//        parent23.addChild(child24);
//        parent23.addChild(child25);
//        parent12.addChild(child26);
//        parent12.addChild(child27);
//        parent13.addChild(child28);
//        parent13.addChild(child29);
//        parent22.addChild(child30);
//        parent22.addChild(child31);
//        parent21.addChild(child32);
//        parent20.addChild(child33);
//        parent19.addChild(child34);
//        parent14.addChild(child35);
//        parent18.addChild(child36);
//        parent17.addChild(child37);
//
//
//
//
//
//        categoryRepository.saveAndFlush(parent);
//        categoryRepository.saveAndFlush(parent1);
//        categoryRepository.saveAndFlush(parent3);
//        categoryRepository.saveAndFlush(parent4);
//        categoryRepository.saveAndFlush(parent5);
//        categoryRepository.saveAndFlush(parent6);
//        categoryRepository.saveAndFlush(parent7);
//        categoryRepository.saveAndFlush(parent8);
//        categoryRepository.saveAndFlush(parent9);
//        categoryRepository.saveAndFlush(parent10);
//        categoryRepository.saveAndFlush(parent11);
//        categoryRepository.saveAndFlush(parent12);
//        categoryRepository.saveAndFlush(parent13);
//        categoryRepository.saveAndFlush(parent14);
//        categoryRepository.saveAndFlush(parent15);
//        categoryRepository.saveAndFlush(parent16);
//        categoryRepository.saveAndFlush(parent17);
//        categoryRepository.saveAndFlush(parent18);
//        categoryRepository.saveAndFlush(parent19);
//        categoryRepository.saveAndFlush(parent20);
//        categoryRepository.saveAndFlush(parent21);
//        categoryRepository.saveAndFlush(parent22);
//        categoryRepository.saveAndFlush(parent23);
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
//        Playlist playlist3Child1 = Playlist.builder()
//                .name("playlist1Child1")
//                .description("playlist_1_Child_1_Desc")
//                .build();
//        Playlist playlist4Child1 = Playlist.builder()
//                .name("playlist2Child1")
//                .description("playlist_2_Child_1_Desc")
//                .build();
//        Playlist playlist5Child1 = Playlist.builder()
//                .name("playlist1Child1")
//                .description("playlist_1_Child_1_Desc")
//                .build();
//        Playlist playlist6Child1 = Playlist.builder()
//                .name("playlist2Child1")
//                .description("playlist_2_Child_1_Desc")
//                .build();
//
//
//        Playlist playlist1Child2 = Playlist.builder()
//                .name("playlist1Child2")
//                .description("playlist_1_Child_2_Desc")
//                .build();
//        Playlist playlist2Child2 = Playlist.builder()
//                .name("playlist2Child2")
//                .description("playlist_2_Child_2_Desc")
//                .build();
//
//        Playlist playlist1Child3 = Playlist.builder()
//                .name("playlist1Child2")
//                .description("playlist_1_Child_2_Desc")
//                .build();
//        Playlist playlist2Child3 = Playlist.builder()
//                .name("playlist2Child2")
//                .description("playlist_2_Child_2_Desc")
//                .build();
//
//        Playlist playlist1Child4 = Playlist.builder()
//                .name("playlist1Child2")
//                .description("playlist_1_Child_2_Desc")
//                .build();
//        Playlist playlist2Child4 = Playlist.builder()
//                .name("playlist2Child2")
//                .description("playlist_2_Child_2_Desc")
//                .build();
//
//        Playlist playlist1Child5 = Playlist.builder()
//                .name("playlist1Child2")
//                .description("playlist_1_Child_2_Desc")
//                .build();
//        Playlist playlist2Child5 = Playlist.builder()
//                .name("playlist2Child2")
//                .description("playlist_2_Child_2_Desc")
//                .build();
//
//        Playlist playlist1Child6 = Playlist.builder()
//                .name("playlist1Child2")
//                .description("playlist_1_Child_2_Desc")
//                .build();
//        Playlist playlist2Child6 = Playlist.builder()
//                .name("playlist2Child2")
//                .description("playlist_2_Child_2_Desc")
//                .build();
//
//        Playlist playlist1Child7 = Playlist.builder()
//                .name("playlist1Child2")
//                .description("playlist_1_Child_2_Desc")
//                .build();
//        Playlist playlist2Child7 = Playlist.builder()
//                .name("playlist2Child2")
//                .description("playlist_2_Child_2_Desc")
//                .build();
//
//        Playlist playlist1Child8 = Playlist.builder()
//                .name("playlist1Child2")
//                .description("playlist_1_Child_2_Desc")
//                .build();
//        Playlist playlist2Child8 = Playlist.builder()
//                .name("playlist2Child2")
//                .description("playlist_2_Child_2_Desc")
//                .build();
//
//        Playlist playlist1Child9 = Playlist.builder()
//                .name("playlist1Child2")
//                .description("playlist_1_Child_2_Desc")
//                .build();
//        Playlist playlist2Child9 = Playlist.builder()
//                .name("playlist2Child2")
//                .description("playlist_2_Child_2_Desc")
//                .build();
//
//        Playlist playlist1Child10 = Playlist.builder()
//                .name("playlist1Child2")
//                .description("playlist_1_Child_2_Desc")
//                .build();
//        Playlist playlist2Child10 = Playlist.builder()
//                .name("playlist2Child2")
//                .description("playlist_2_Child_2_Desc")
//                .build();
//
//        Playlist playlist1Child11 = Playlist.builder()
//                .name("playlist1Child2")
//                .description("playlist_1_Child_2_Desc")
//                .build();
//        Playlist playlist2Child11 = Playlist.builder()
//                .name("playlist2Child2")
//                .description("playlist_2_Child_2_Desc")
//                .build();
//
//
//
//
//
//
//
//
//        playlistRepository.saveAllAndFlush(List.of(playlist1Child1,playlist1Child2,playlist2Child1,playlist2Child2,
//                playlist3Child1,playlist4Child1,playlist5Child1,playlist6Child1,
//                playlist1Child3,playlist2Child3,playlist1Child4,playlist2Child4,
//                playlist1Child5,playlist2Child5,playlist1Child6,playlist2Child6,
//                playlist1Child7,playlist2Child7,playlist1Child8,playlist2Child8,
//                playlist1Child9,playlist2Child9,playlist1Child10,playlist2Child10,
//                playlist1Child11,playlist2Child11));
//        child1.addPlaylist(playlist1Child1);
//        child1.addPlaylist(playlist2Child1);
//        child1.addPlaylist(playlist3Child1);
//        child1.addPlaylist(playlist4Child1);
//        child1.addPlaylist(playlist5Child1);
//        child1.addPlaylist(playlist6Child1);
//
//        child2.addPlaylist(playlist1Child2);
//        child2.addPlaylist(playlist2Child2);
//
//        child3.addPlaylist(playlist1Child3);
//        child3.addPlaylist(playlist2Child3);
//
//        child4.addPlaylist(playlist1Child4);
//        child4.addPlaylist(playlist2Child4);
//
//        child5.addPlaylist(playlist1Child5);
//        child5.addPlaylist(playlist2Child5);
//
//        child6.addPlaylist(playlist1Child6);
//        child6.addPlaylist(playlist2Child6);
//
//        child7.addPlaylist(playlist1Child7);
//        child7.addPlaylist(playlist2Child7);
//
//        child8.addPlaylist(playlist1Child8);
//        child8.addPlaylist(playlist2Child8);
//
//        child9.addPlaylist(playlist1Child9);
//        child9.addPlaylist(playlist2Child9);
//
//        child10.addPlaylist(playlist1Child10);
//        child10.addPlaylist(playlist2Child10);
//
//        child11.addPlaylist(playlist1Child11);
//        child11.addPlaylist(playlist2Child11);
//
//        categoryRepository.saveAll(List.of(child1,child2,child3,child5,child4,child6,child7,child8,child9,child10,child11));
//
//        playlist1Child1.addSong(song1);
//        playlist1Child1.addSong(song2);
//        playlist2Child1.addSong(song5);
//        playlist2Child1.addSong(song6);
//
//
//        playlist1Child2.addSong(song3);
//        playlist1Child2.addSong(song4);
//
//        playlist2Child2.addSong(song7);
//        playlist2Child2.addSong(song8);
//        playlistRepository.saveAllAndFlush(List.of(playlist1Child1,playlist1Child2,playlist2Child1,playlist2Child2,
//                playlist3Child1,playlist4Child1,playlist5Child1,playlist6Child1,
//                playlist1Child3,playlist2Child3,playlist1Child4,playlist2Child4,
//                playlist1Child5,playlist2Child5,playlist1Child6,playlist2Child6,
//                playlist1Child7,playlist2Child7,playlist1Child8,playlist2Child8,
//                playlist1Child9,playlist2Child9,playlist1Child10,playlist2Child10,
//                playlist1Child11,playlist2Child11));
//    }

}

package com.spotify.app.album;


import com.spotify.app.dto.AlbumDTO;
import com.spotify.app.dto.response.SongResponse;
import com.spotify.app.dto.response.UserNoAssociationResponse;
import com.spotify.app.mapper.AlbumMapper;
import com.spotify.app.mapper.AlbumRequestMapper;
import com.spotify.app.mapper.AlbumResponseMapper;
import com.spotify.app.mapper.SongResponseMapper;
import com.spotify.app.model.Album;
import com.spotify.app.model.AlbumSong;
import com.spotify.app.model.Song;
import com.spotify.app.model.User;
import com.spotify.app.repository.AlbumRepository;
import com.spotify.app.repository.AlbumSongRepository;
import com.spotify.app.repository.SongRepository;
import com.spotify.app.repository.UserRepository;
import com.spotify.app.service.AlbumService;
import com.spotify.app.service.CloudinaryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AlbumServiceTest {

    @Mock
    private  AlbumRepository albumRepository ;
    @Mock
    private  SongRepository songRepository ;
    @Mock
    private  AlbumSongRepository albumSongRepository;
    @Mock
    private  AlbumMapper albumMapper ;
    @Mock
    private  UserRepository userRepository;
    @Mock
    private  AlbumResponseMapper albumResponseMapper;
    @Mock
    private  AlbumRequestMapper albumRequestMapper;
    @Mock
    private  SongResponseMapper songResponseMapper;
    @Mock
    private  CloudinaryService cloudinaryService;

    private AlbumService albumService;
    private AlbumDTO albumDTO ;

    private Album album;

    private Song song1;
    private Song song2;

    private User user ;

    private SongResponse songResponse1;
    private SongResponse songResponse2;
    private UserNoAssociationResponse artist;
    private List<AlbumSong> albumSongs;


    @BeforeEach
    void setUp () {
        albumService = new AlbumService(albumRepository, songRepository, albumSongRepository, albumMapper, userRepository, albumResponseMapper, albumRequestMapper, songResponseMapper, cloudinaryService);
        artist = new UserNoAssociationResponse(1L);
        songResponse1 = new SongResponse(1L);
        songResponse2 = new SongResponse(2L);
        song1 = new Song(1L);
        song2 = new Song(2L);

        user = new User(1L);

        List<SongResponse> songs = List.of(songResponse1, songResponse2);
        albumDTO = new AlbumDTO(1L,
                "album_name",
                "11-11-2023",
                artist,
                "image.png",
                "thumbnail.png",
                0,
                "4:12",
                true,
                songs);
        album = Album.builder()
                .id(1L)
                .name("album_name")
                .releaseDate(LocalDateTime.of(2023, 11, 11, 0, 0))
                .user(user)
                .build();

        albumSongs = List.of(
                AlbumSong.builder()
                        .song(song1)
                        .album(album)
                        .build(),
                AlbumSong.builder()
                        .song(song2)
                        .album(album)
                        .build());
    }

    @Test
    void canGetAlbumById () {
        // given
        Long albumId = 1L ;

        // when
        when(albumRepository.findById(albumId)).thenReturn(Optional.of(album));

        // then
        var actual = albumService.get(albumId);
        assertThat(actual).isEqualTo(album);
    }

    // add song
//    @Test
//    public void canAddSong ()  {
//        // given
//        Long albumId = 2L;
//        Long songId = 1L;
//        Album album2 = new Album(albumId);
//        Song song3 = new Song(songId);
//        AlbumSong albumSong = new AlbumSong(album2, song3);
//
//        List<AlbumSong> albumSongList = List.of(albumSong);
//        album2.setAlbumSongList(albumSongList);
//        // when
//        when(albumRepository.findById(albumId)).thenReturn(Optional.of(album2));
//        when(songRepository.findById(songId)).thenReturn(Optional.of(song3));
//        albumService.addSong(albumId, songId);
//    }

    // remove song
    @Test
    public void canRemoveSong () {

    }

    // add album

    @Test
    public void canAddAlbum () {

    }
    // remove album
    @Test
    public void canRemoveAlbum () {

    }

    // update status
    @Test
    public void canUpdateStatus () {

    }



}

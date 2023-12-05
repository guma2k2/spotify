package com.spotify.app.song;


import com.github.javafaker.Faker;
import com.spotify.app.dto.SongDTO;
import com.spotify.app.enums.Genre;
import com.spotify.app.exception.ResourceNotFoundException;
import com.spotify.app.mapper.AlbumResponseMapper;
import com.spotify.app.mapper.SongMapper;
import com.spotify.app.mapper.SongResponseMapper;
import com.spotify.app.mapper.SongSearchResponseMapper;
import com.spotify.app.model.Playlist;
import com.spotify.app.model.PlaylistSong;
import com.spotify.app.model.Song;
import com.spotify.app.repository.*;
import com.spotify.app.service.CloudinaryService;
import com.spotify.app.service.ReviewService;
import com.spotify.app.service.SongService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class SongServiceTest {

    @Mock
    private  SongRepository songRepository ;
    @Mock
    private  AlbumSongRepository albumSongRepository;
    @Mock
    private  PlaylistSongRepository playlistSongRepository;
    @Mock
    private  SongResponseMapper songResponseMapper;
    @Mock
    private  SongMapper songMapper;
    @Mock
    private  AlbumResponseMapper albumResponseMapper;
    @Mock
    private  UserRepository userRepository ;
    @Mock
    private  AlbumRepository albumRepository;
    @Mock
    private  ReviewService reviewService ;
    @Mock
    private  RestTemplate restTemplate;
    @Mock
    private  SongSearchResponseMapper songSearchResponseMapper;
    @Mock
    private CloudinaryService cloudinaryService;
    private SongService underTest ;
    @BeforeEach
    public void setUp() {
        underTest = new SongService(
                songRepository,
                albumSongRepository,
                playlistSongRepository,
                songResponseMapper,
                songMapper,
                albumResponseMapper,
                userRepository,
                albumRepository,
                reviewService,
                restTemplate,
                songSearchResponseMapper,
                cloudinaryService
        ) ;
    }
    @Test
    public void canGetSongById() {
        // given
        Faker faker = new Faker();
        Long songId = 1L;
        Song expected = Song
                .builder()
                .id(songId)
                .genre(Genre.CLASSICAL)
                .name(faker.funnyName().name())
                .duration(222)
                .build();
        // when
        when(songRepository.findById(songId)).thenReturn(Optional.of(expected));
        // then
        Song actual = underTest.get(songId);
        assertThat(actual.getName()).isEqualTo(expected.getName());
    }

    @Test
    public void canNotGetSongById() {
        // given
        Long songId = 0L;
        // when
        when(songRepository.findById(songId)).thenReturn(Optional.empty());
        Throwable throwable = assertThrows(ResourceNotFoundException.class, () -> underTest.get(songId));
        // then
        String expected = String.format("song with id:%d not found",songId);
        String actual = throwable.getMessage();
        assertEquals(expected,actual);
    }

    @Test
    public void canFindByPlaylistId() {
        // given
        Long playlistId = 1L;
        Playlist playlist = Playlist.builder()
                .id(playlistId)
                .build();
        Song song1 = Song.builder().id(1L).build();
        Song song2 = Song.builder().id(2L).build();
        SongDTO songDTO1= new SongDTO(1L);
        SongDTO songDTO2= new SongDTO(2L);

        List<Song> songs = List.of(song1,song2);
        List<SongDTO> expected = List.of(songDTO1,songDTO2);

        List<PlaylistSong> playlistSongs = List.of(
                PlaylistSong.builder().playlist(playlist).song(song1).build(),
                PlaylistSong.builder().playlist(playlist).song(song2).build()
        );
        // when
        when(playlistSongRepository.findByPlaylistId(playlistId)).thenReturn(playlistSongs);
        when(songMapper.songsToSongsDTO(songs)).thenReturn(expected);

        // then
        List<SongDTO> actual = underTest.findByPlaylistId(playlistId);

        assertThat(actual).isEqualTo(expected);

    }
    @Test
    public void canFindByName() {
        // given
        Faker faker = new Faker();
        String songName = faker.funnyName().name();
        Song song = new Song(songName);
        boolean expected = true;
        // when
        when(songRepository.findByName(songName)).thenReturn(Optional.of(song));
        // then
        var actual = underTest.checkSongExitByName(songName);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldNotFindByName() {
        // given
        Faker faker = new Faker();
        String songName = faker.funnyName().name();
        boolean expected = false;
        // when
        when(songRepository.findByName(songName)).thenReturn(Optional.empty());
        // then
        var actual = underTest.checkSongExitByName(songName);
        assertThat(actual).isEqualTo(expected);
    }
}

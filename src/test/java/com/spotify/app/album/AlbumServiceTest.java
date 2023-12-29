package com.spotify.app.album;


import com.spotify.app.dto.AlbumDTO;
import com.spotify.app.dto.request.AlbumRequest;
import com.spotify.app.dto.response.SongResponse;
import com.spotify.app.dto.response.UserNoAssociationResponse;
import com.spotify.app.exception.ResourceNotFoundException;
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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
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

    @Test
    public void canAddSong ()  {
        Long albumId = 1L;
        Long songId = 2L;

        // Mocking the Album and Song
        Album album = new Album();
        album.setId(albumId);

        Song song = new Song();
        song.setId(songId);

        // Mocking the repository responses
        Mockito.when(albumRepository.findById(albumId)).thenReturn(Optional.of(album));
        Mockito.when(songRepository.findById(songId)).thenReturn(Optional.of(song));
        Mockito.when(albumRepository.save(Mockito.any(Album.class))).thenReturn(album);

        // Calling the service method
        albumService.addSong(albumId, songId);

        // Verifying that the repository methods were called
        Mockito.verify(albumRepository, Mockito.times(1)).findById(albumId);
        Mockito.verify(songRepository, Mockito.times(1)).findById(songId);
        Mockito.verify(albumRepository, Mockito.times(1)).save(album);
    }

    @Test
    public void shouldThrowExceptionWithNonexistentAlbum () {
        Long albumId = 1L;
        Long songId = 2L;
        Mockito.when(albumRepository.findById(albumId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            albumService.addSong(albumId, songId);
        });
        Mockito.verify(albumRepository, Mockito.times(1)).findById(albumId);
        Mockito.verify(songRepository, Mockito.never()).findById(albumId);
        Mockito.verify(albumRepository, Mockito.never()).save(Mockito.any(Album.class));

    }
    // remove song
    @Test
    public void canRemoveSong() {
        // Sample data
        Long albumId = 1L;
        Long songId = 2L;

        // Mocking the Album and Song
        Album album = new Album();
        album.setId(albumId);

        Song song = new Song();
        song.setId(songId);

        // Mocking the repository responses
        Mockito.when(albumRepository.findById(albumId)).thenReturn(Optional.of(album));
        Mockito.when(songRepository.findById(songId)).thenReturn(Optional.of(song));
        Mockito.when(albumRepository.save(Mockito.any(Album.class))).thenReturn(album);

        // Calling the service method
        albumService.removeSong(albumId, songId);

        // Verifying that the repository methods were called
        Mockito.verify(albumRepository, Mockito.times(1)).findById(albumId);
        Mockito.verify(songRepository, Mockito.times(1)).findById(songId);
        Mockito.verify(albumRepository, Mockito.times(1)).save(album);
    }

    @Test
    public void cannotRemoveSongWithNonexistentAlbum() {
        // Sample data
        Long albumId = 1L;
        Long songId = 2L;

        // Mocking the repository responses
        Mockito.when(albumRepository.findById(albumId)).thenReturn(Optional.empty());

        // Calling the service method and expecting an exception
        assertThrows(ResourceNotFoundException.class, () -> {
            albumService.removeSong(albumId, songId);
        });

        // Verifying that the repository methods were called
        Mockito.verify(albumRepository, Mockito.times(1)).findById(albumId);
        Mockito.verify(songRepository, Mockito.never()).findById(songId);
        Mockito.verify(albumRepository, Mockito.never()).save(Mockito.any(Album.class));
    }


    @Test
    public void canAddAlbum () {
        // given
        Long userId = 1L;
        Long albumId= 1L;
        AlbumRequest request = new AlbumRequest("album_name");
        Album album1 = new Album(albumId,"album_name");
        User user1 = new User(userId);

        // when
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user1));
        Mockito.when(albumRequestMapper.dtoToEntity(request)).thenReturn(album1);
        Mockito.when(albumRepository.save(album1)).thenReturn(album1);
        var actualId = albumService.addAlbum(userId, request);

        // then
        assertEquals(actualId, albumId);
        Mockito.verify(userRepository, Mockito.times(1)).findById(userId);
        Mockito.verify(albumRepository, Mockito.times(1)).save(Mockito.any(Album.class));
    }

    @Test
    public void cannotAddAlbumWithNonexistentUser() {
        // Sample data
        Long userId = 1L;
        String albumName = "albumName";
        AlbumRequest albumRequest = new AlbumRequest(albumName);

        // Mocking the UserRepository response
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Calling the service method and expecting an exception
        assertThrows(ResourceNotFoundException.class, () -> {
            albumService.addAlbum(userId, albumRequest);
        });

        // Verifying that the repository methods were called
        Mockito.verify(userRepository, Mockito.times(1)).findById(userId);
        Mockito.verify(albumRequestMapper, Mockito.never()).dtoToEntity(Mockito.any(AlbumRequest.class));
        Mockito.verify(albumRepository, Mockito.never()).save(Mockito.any(Album.class));
    }
    // remove album
    @Test
    public void canUpdateAlbum () {
        // given
        Long albumId = 1L;
        String updatedName = "Updated Album Name";
        Album existingAlbum = new Album();
        existingAlbum.setId(albumId);
        existingAlbum.setName("Old Album Name");
        AlbumRequest request = new AlbumRequest(updatedName);

        // when
        Mockito.when(albumRepository.findById(albumId)).thenReturn(Optional.of(existingAlbum));
        Mockito.when(albumRepository.save(Mockito.any(Album.class))).thenReturn(existingAlbum);
        albumService.updateAlbum(albumId, request);
        // then
        Mockito.verify(albumRepository, Mockito.times(1)).findById(albumId);
        Mockito.verify(albumRepository, Mockito.times(1)).save(existingAlbum);
        assertEquals(updatedName, existingAlbum.getName());

    }

    @Test
    public void cannotUpdateAlbumWithNonexistentAlbum() {
        // Sample data
        Long albumId = 1L;
        String updatedName = "Updated Album Name";

        // Mocking the AlbumRepository response
        Mockito.when(albumRepository.findById(albumId)).thenReturn(Optional.empty());

        // Calling the service method and expecting an exception
        assertThrows(ResourceNotFoundException.class, () -> {
            AlbumRequest updateRequest = new AlbumRequest(updatedName);
            albumService.updateAlbum(albumId, updateRequest);
        });

        // Verifying that the repository methods were called
        Mockito.verify(albumRepository, Mockito.times(1)).findById(albumId);
        Mockito.verify(albumRepository, Mockito.never()).save(Mockito.any(Album.class));
    }

    // update status
    @Test
    public void canUpdateStatus () {
        Long albumId = 1L;

        // Mocking the AlbumRepository response
        Album existingAlbum = new Album();
        existingAlbum.setId(albumId);
        existingAlbum.setStatus(true);

        Mockito.when(albumRepository.findById(albumId)).thenReturn(Optional.of(existingAlbum));
        existingAlbum.setStatus(false);
        Mockito.when(albumRepository.saveAndFlush(Mockito.any(Album.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Calling the service method
        String result = albumService.updateStatusAlbum(albumId);

        // Verifying that the repository methods were called
        Mockito.verify(albumRepository, Mockito.times(1)).findById(albumId);
        Mockito.verify(albumRepository, Mockito.times(1)).saveAndFlush(existingAlbum);

        // Verifying the result
        String expectedStatusMessage = "album with id: 1 is disabled"; // Assuming initial status is true
        assertEquals(expectedStatusMessage, result);
    }



}

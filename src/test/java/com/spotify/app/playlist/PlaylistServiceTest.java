package com.spotify.app.playlist;

import com.spotify.app.exception.ResourceNotFoundException;
import com.spotify.app.mapper.PlaylistMapper;
import com.spotify.app.mapper.PlaylistResponseMapper;
import com.spotify.app.model.Playlist;
import com.spotify.app.repository.PlaylistRepository;
import com.spotify.app.repository.PlaylistSongRepository;
import com.spotify.app.repository.PlaylistUserRepository;
import com.spotify.app.service.CloudinaryService;
import com.spotify.app.service.PlaylistService;
import com.spotify.app.service.SongService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class PlaylistServiceTest {

    private PlaylistService underTest;

    @Mock
    private  PlaylistRepository playlistRepository ;
    @Mock
    private  PlaylistMapper playlistMapper;
    @Mock
    private  SongService songService;
    @Mock
    private  PlaylistSongRepository playlistSongRepository;
    @Mock
    private  PlaylistResponseMapper playlistResponseMapper;
    @Mock
    private  PlaylistUserRepository playlistUserRepository;
    @Mock
    private  CloudinaryService cloudinaryService;

    @BeforeEach
    void setUp () {
        underTest = new PlaylistService(playlistRepository, playlistMapper, songService, playlistSongRepository, playlistResponseMapper,playlistUserRepository, cloudinaryService);
    }

    @Test
    @DisplayName("should save playlist success")
    public void canSavePlaylist () {
        // given
        Long expect = 1L;
        String playlistName = "name";
        String playlistDesc = "desc";
        Playlist playlist = Playlist
                .builder()
                .id(expect)
                .name(playlistName)
                .description(playlistDesc)
                .build();
        Mockito.when(playlistRepository.save(Mockito.any(Playlist.class))).thenReturn(playlist);

        // when
        var actual = underTest.addPlaylist(playlistName, playlistDesc);

        // then
        Mockito.verify(playlistRepository, Mockito.times(1)).save(Mockito.any(Playlist.class));
        assertThat(actual).isEqualTo(expect);
    }

    @Test
    @DisplayName("should update playlist by id success")
    public void canUpdatePlaylist () {

        // given
        Long playlistId = 1L; // Replace with an actual playlist ID
        String updatedDescription = "Updated Description";
        String updatedName = "Updated Playlist";

        Playlist existingPlaylist = new Playlist();
        existingPlaylist.setId(playlistId);
        existingPlaylist.setDescription("Initial Description");
        existingPlaylist.setName("Initial Playlist");

        Mockito.when(playlistRepository.save(Mockito.any(Playlist.class))).thenReturn(existingPlaylist);
        Mockito.when(playlistRepository.findById(playlistId)).thenReturn(Optional.of(existingPlaylist));

        // when
        underTest.updatePlaylist(playlistId, updatedDescription, updatedName);

        // then
        Mockito.verify(playlistRepository, Mockito.times(1)).findById(playlistId);
        Mockito.verify(playlistRepository, Mockito.times(1)).save(Mockito.any(Playlist.class));

        Assertions.assertEquals(updatedDescription, existingPlaylist.getDescription());
        Assertions.assertEquals(updatedName, existingPlaylist.getName());
    }

    @Test
    @DisplayName("should get playlist by id success")
    public void canGetById () {
        // given
        Long playlistId = 1L;
        Playlist expect = new Playlist(playlistId);
        Mockito.when(playlistRepository.findById(playlistId)).thenReturn(Optional.of(expect));

        // when
        var actual = underTest.get(playlistId);

        // then
        Mockito.verify(playlistRepository, Mockito.times(1)).findById(playlistId);
        Assertions.assertEquals(playlistId, actual.getId());
    }

    @Test
    @DisplayName("should not get playlist by id with not existing playlist")
    public void cannotGetById () {
        // given
        Long playlistId = 1L;
        String expectMessage = String.format("playlist with id: [%d] not found", playlistId);
        Mockito.when(playlistRepository.findById(playlistId)).thenReturn(Optional.empty());

        // when
        // then
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            underTest.get(playlistId);
        });
        Mockito.verify(playlistRepository, Mockito.times(1)).findById(playlistId);
    }
}

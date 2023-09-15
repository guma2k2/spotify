package com.spotify.app.service;


import com.spotify.app.dto.PlaylistDTO;
import com.spotify.app.dto.PlaylistResponseDTO;
import com.spotify.app.dto.SongDTO;
import com.spotify.app.exception.UserException;
import com.spotify.app.mapper.PlaylistMapper;
import com.spotify.app.mapper.PlaylistResponseMapper;
import com.spotify.app.mapper.SongMapper;
import com.spotify.app.model.Playlist;
import com.spotify.app.model.PlaylistSong;
import com.spotify.app.model.Song;
import com.spotify.app.repository.PlaylistRepository;
import com.spotify.app.repository.PlaylistSongRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class PlaylistService {

    private final PlaylistRepository playlistRepository ;

    private final PlaylistSongRepository playlistSongRepository ;

    public List<PlaylistResponseDTO> findByUserId(Long userId) {
        List<Playlist> playlists = playlistRepository.findByUserId(userId) ;
        return PlaylistResponseMapper.INSTANCE.playlistsToPlaylistsDTO(playlists);
    }



    public PlaylistDTO findByIdWithSongs(Long playlistId) {

        // Find playlistSong by playlistId
        List<PlaylistSong> playlistSongs = playlistSongRepository.findByPlaylistId(playlistId);


        // Map List playlistSong to Song's list
        List<Song> songs = playlistSongs.stream().map(playlistSong -> playlistSong.getSong()).collect(Collectors.toList());

        // Map Song to Song DTO
        List<SongDTO> songDTOS = SongMapper.INSTANCE.songsToSongsDTO(songs);
        // Get playlist by Id
        Playlist playlist = playlistRepository.findById(playlistId).orElseThrow(() -> new UserException("Playlist not found"));

        return PlaylistMapper.INSTANCE.playlistToPlaylistDTO(playlist, songDTOS);
    }
}

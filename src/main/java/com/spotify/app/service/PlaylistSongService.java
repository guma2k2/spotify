package com.spotify.app.service;

import com.spotify.app.model.PlaylistSong;
import com.spotify.app.model.Song;
import com.spotify.app.repository.PlaylistSongRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class PlaylistSongService {
    private final PlaylistSongRepository playlistSongRepository;

    public Song findByPlaylistIdAndSongId(Long playlistId, Long songId) {
        Optional<PlaylistSong> playlistSong = playlistSongRepository.findBySongIdAndPlaylistId(songId, playlistId);
        if (playlistSong.isPresent()) {
            return playlistSong.get().getSong();
        }
        return null;
    }
}

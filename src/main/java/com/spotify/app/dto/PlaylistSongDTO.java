package com.spotify.app.dto;

import com.spotify.app.dto.PlaylistDTO;
import com.spotify.app.dto.SongDTO;

import java.time.LocalDateTime;

public record PlaylistSongDTO(PlaylistDTO playlist, SongDTO song, LocalDateTime createdOn) {
}

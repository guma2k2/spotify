package com.spotify.app.dto;

import java.time.LocalDateTime;

public record PlaylistSongDTO(PlaylistDTO playlist, SongDTO song, LocalDateTime createdOn) {
}

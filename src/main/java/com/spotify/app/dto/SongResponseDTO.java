package com.spotify.app.dto;

import com.spotify.app.enums.Genre;

import java.time.LocalDateTime;
import java.util.Set;

public record SongResponseDTO(Long id,
                              String name,
                              String lyric,
                              LocalDateTime releaseDate,
                              Genre genre ,
                              int duration,
                              String audioPath,
                              Set<UserResponseDTO> users) {
}

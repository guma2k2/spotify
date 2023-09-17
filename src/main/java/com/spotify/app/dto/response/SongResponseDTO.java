package com.spotify.app.dto.response;

import com.spotify.app.enums.Genre;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public record SongResponseDTO(Long id,
                              String name,
                              String lyric,
                              LocalDateTime releaseDate,
                              Genre genre ,
                              LocalDateTime createdAt,
                              String duration,
                              String audioPath,
                              String imagePath,
                              Set<UserResponseDTO> users,

                              List<AlbumResponseDTO> albums) {
}

package com.spotify.app.dto;

import com.spotify.app.enums.Genre;

import java.time.LocalDateTime;
import java.util.Set;

public record SongDTO (Long id,
                       String name,
                       String lyric,
                       String releaseDate,
                       Genre genre ,
                       String duration,
                       long viewCount,
                       String audioPath,
                       String imagePath
) {
}

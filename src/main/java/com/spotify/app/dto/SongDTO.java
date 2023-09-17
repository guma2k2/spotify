package com.spotify.app.dto;

import com.spotify.app.enums.Genre;

import java.time.LocalDateTime;
import java.util.Set;

public record SongDTO (Long id,
                       String name,
                       String lyric,
                       LocalDateTime releaseDate,
                       Genre genre ,
                       String duration,
                       String audioPath,
                       String imagePath
                       ) {
}

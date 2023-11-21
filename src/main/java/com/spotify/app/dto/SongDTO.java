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
                       boolean status,
                       String label,
                       String audioPath,
                       String imagePath
) {

    public SongDTO(Long id) {
        this(id, null, null, null, null, null, 0, false, null, null, null);
    }
}

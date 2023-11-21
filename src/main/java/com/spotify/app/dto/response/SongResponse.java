package com.spotify.app.dto.response;

import com.spotify.app.enums.Genre;

import java.util.List;
import java.util.Set;

public record SongResponse(Long id,
                           String name,
                           String lyric,
                           String releaseDate,
                           Genre genre ,
                           String createdAt,
                           String duration,
                           long viewCount,
                           String audioPath,
                           String imagePath,
                           boolean status,
                           String label,
                           Set<UserNoAssociationResponse> users,
                           List<AlbumResponse> albums,
                           List<ReviewResponse> reviews
) {

    public SongResponse(Long id) {
        this(id, null, null, null, null, null, null, 0, null, null, false, null, null, null, null);
    }
}

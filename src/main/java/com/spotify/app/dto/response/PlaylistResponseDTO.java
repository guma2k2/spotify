package com.spotify.app.dto.response;

public record PlaylistResponseDTO( Long id,
                                   String name,
                                   String description,
                                   String imagePath,
                                   String thumbnailPath,
                                   int sumSongCount,
                                   long sumViewCount,
                                   long likedCount ){
}

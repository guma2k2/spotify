package com.spotify.app.dto.response;

public record PlaylistResponse(Long id,
                               String name,
                               String description,
                               String imagePath,
                               String thumbnailPath,
                               int sumSongCount,
                               String totalTime,
                               long likedCount
){
}

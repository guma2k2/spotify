package com.spotify.app.dto.response;

public record AlbumResponse(Long id,
                            String name,
                            String releaseDate,
                            String totalTime,
                            String imagePath,
                            String thumbnailPath,
                            boolean status,
                            UserNoAssociationResponse user
) {
}

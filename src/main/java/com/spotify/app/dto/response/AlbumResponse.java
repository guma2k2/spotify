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
    public AlbumResponse(Long id) {
        this(id, null, null, null, null, null, false, null);
    }
}

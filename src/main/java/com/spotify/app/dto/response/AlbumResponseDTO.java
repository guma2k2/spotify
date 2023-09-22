package com.spotify.app.dto.response;

import java.time.LocalDateTime;

public record AlbumResponseDTO(Long id, String name, String releaseDate, String totalTime, String imagePath, String thumbnailPath) {
}

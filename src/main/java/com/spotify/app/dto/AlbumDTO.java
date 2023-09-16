package com.spotify.app.dto;

import java.time.LocalDateTime;
import java.util.List;

public record AlbumDTO(Long id, String name, LocalDateTime releaseDate, UserResponseDTO user, String totalTime,String imagePath, String thumbnailPath, List<SongDTO> songs) {
}

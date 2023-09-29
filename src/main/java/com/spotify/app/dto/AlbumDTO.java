package com.spotify.app.dto;

import com.spotify.app.dto.response.UserResponseDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record AlbumDTO(Long id, @NotBlank String name, String releaseDate, UserResponseDTO user, String totalTime, String imagePath, String thumbnailPath, List<SongDTO> songs) {
}

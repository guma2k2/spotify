package com.spotify.app.dto;

import com.spotify.app.dto.response.UserResponseDTO;
import com.spotify.app.dto.response.UserResponseNoAssociation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record AlbumDTO(Long id, @NotBlank String name, String releaseDate, UserResponseNoAssociation user, String imagePath, String thumbnailPath, int songCount, String totalTime, List<SongDTO> songs) {
}

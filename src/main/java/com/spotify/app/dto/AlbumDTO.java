package com.spotify.app.dto;

import com.spotify.app.dto.response.UserNoAssociationResponse;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record AlbumDTO(Long id,
                       @NotBlank String name,
                       String releaseDate,
                       UserNoAssociationResponse user,
                       String imagePath,
                       String thumbnailPath,
                       int songCount,
                       String totalTime,
                       List<SongDTO> songs) {
}

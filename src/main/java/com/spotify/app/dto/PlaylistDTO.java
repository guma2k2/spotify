package com.spotify.app.dto;

import java.util.List;

public record PlaylistDTO(Long id,
                          String name,
                          String description,
                          String imagePath, String thumbnailPath,
                          UserResponseDTO user,
                          List<SongDTO> songs) {
}

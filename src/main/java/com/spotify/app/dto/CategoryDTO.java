package com.spotify.app.dto;

import java.util.Set;

public record CategoryDTO(Integer id,
                          String title,
                          String imagePath, String thumbnailPath,
                          Set<PlaylistResponseDTO> playlists
                          ) {
}

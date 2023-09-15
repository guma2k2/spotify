package com.spotify.app.dto;

import java.util.Set;

public record CategoryDTO(Integer id,
                          String title,
                          Set<PlaylistResponseDTO> playlists
                          ) {
}

package com.spotify.app.dto;

import com.spotify.app.dto.response.PlaylistResponse;
import java.util.Set;

public record CategoryDTO(Integer id,
                          String title,
                          boolean status,
                          String imagePath,
                          String thumbnailPath,
                          Set<PlaylistResponse> playlists
) {
    public CategoryDTO(Integer id) {
        this(id, null, false, null, null, null);
    }
}

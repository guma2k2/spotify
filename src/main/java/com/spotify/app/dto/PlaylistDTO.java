package com.spotify.app.dto;

import com.spotify.app.dto.response.SongResponseDTO;
import com.spotify.app.dto.response.UserResponseDTO;

import java.util.List;

public record PlaylistDTO(Long id,
                          String name,
                          String description,
                          String imagePath,
                          String thumbnailPath,
                          UserResponseDTO user,
                          int sumSongCount,
                          long sumViewCount,
                          long likedCount,
                          List<SongResponseDTO> songs) {
}

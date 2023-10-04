package com.spotify.app.dto;

import com.spotify.app.dto.response.SongResponse;

import java.util.List;

public record PlaylistDTO(Long id,
                          String name,
                          String description,
                          String imagePath,
                          String thumbnailPath,
                          int sumSongCount,
                          String totalTime,
                          long likedCount,
                          List<SongResponse> songs) {
}

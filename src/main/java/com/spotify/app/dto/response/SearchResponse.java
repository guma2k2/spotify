package com.spotify.app.dto.response;

import java.util.List;

public record SearchResponse(
        List<SongSearchResponse> songs,
        List<UserResponse> users,
        List<PlaylistResponse> playlists,
        List<AlbumResponse> albums
) {
}

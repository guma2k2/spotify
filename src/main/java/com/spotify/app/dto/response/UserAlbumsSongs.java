package com.spotify.app.dto.response;

import java.util.List;

public record UserAlbumsSongs(List<AlbumResponse> albums , List<SongResponse> songs) {
}

package com.spotify.app.dto.response;

import com.spotify.app.enums.Genre;

import java.util.Set;

public record SongSearchResponse(
        Long id,
        String name,
        String audioPath,
        Set<UserNoAssociationResponse> users
) {
}

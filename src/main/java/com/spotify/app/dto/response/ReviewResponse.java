package com.spotify.app.dto.response;

import com.spotify.app.dto.response.UserNoAssociationResponse;

public record ReviewResponse(
        Long id,
        UserNoAssociationResponse user,
        String createdAt,
        String content,
        boolean status,
        String label
) {
}

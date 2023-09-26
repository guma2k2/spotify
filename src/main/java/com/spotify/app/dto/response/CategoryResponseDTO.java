package com.spotify.app.dto.response;

public record CategoryResponseDTO (Integer id, String title, String imagePath, String thumbnailPath, String categoryParentTitle) {
}

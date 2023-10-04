package com.spotify.app.dto.response;

public record CategoryResponse(Integer id, String title, String imagePath, String thumbnailPath, String categoryParentTitle) {
}

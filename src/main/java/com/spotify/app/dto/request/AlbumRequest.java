package com.spotify.app.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AlbumRequest (@NotBlank String name) {
}

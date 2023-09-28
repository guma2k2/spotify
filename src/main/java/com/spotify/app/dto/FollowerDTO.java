package com.spotify.app.dto;

import com.spotify.app.dto.response.UserResponseDTO;

import java.util.Set;

public record FollowerDTO(Set<UserResponseDTO> followers, Set<UserResponseDTO> followings) {
}

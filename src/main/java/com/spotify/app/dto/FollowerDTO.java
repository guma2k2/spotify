package com.spotify.app.dto;

import com.spotify.app.dto.response.UserResponse;

import java.util.Set;

public record FollowerDTO(Set<UserResponse> followers, Set<UserResponse> followings) {
}

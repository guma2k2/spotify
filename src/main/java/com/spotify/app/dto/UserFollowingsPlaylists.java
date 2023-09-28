package com.spotify.app.dto;

import com.spotify.app.dto.response.PlaylistResponseDTO;
import com.spotify.app.dto.response.UserResponseDTO;

import java.util.*;

public record UserFollowingsPlaylists(List<UserResponseDTO> followings, List<PlaylistResponseDTO> playlists) {
}

package com.spotify.app.dto;

import com.spotify.app.dto.response.PlaylistResponseDTO;
import com.spotify.app.dto.response.UserResponseNoAssociation;

import java.util.*;

public record UserFollowingsPlaylists(List<UserResponseNoAssociation> followings, List<PlaylistResponseDTO> playlists) {
}

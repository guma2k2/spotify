package com.spotify.app.dto.response;

import com.spotify.app.dto.response.PlaylistResponse;
import com.spotify.app.dto.response.UserNoAssociationResponse;

import java.util.*;

public record UserFollowingsPlaylists(List<UserNoAssociationResponse> followings, List<PlaylistResponse> playlists) {
}

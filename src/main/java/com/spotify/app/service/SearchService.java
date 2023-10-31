package com.spotify.app.service;

import com.spotify.app.dto.response.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class SearchService {

    private final SongService songService;
    private final AlbumService albumService;
    private final PlaylistService playlistService;
    private final UserService userService;

    public SearchResponse findByName(String name) {
        List<SongSearchResponse> songs = songService.findByNameFullText(name);
        List<UserResponse> users = userService.findAllArtistByUserName(name);
        List<PlaylistResponse> playlists = playlistService.findAllByName(name);
        List<AlbumResponse> albums = albumService.findAllByName(name);
        return new SearchResponse(songs, users, playlists, albums);
    }



}

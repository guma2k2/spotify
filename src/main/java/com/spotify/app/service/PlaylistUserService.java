package com.spotify.app.service;

import com.spotify.app.dto.response.PlaylistResponse;
import com.spotify.app.mapper.PlaylistResponseMapper;
import com.spotify.app.model.Playlist;
import com.spotify.app.model.PlaylistUser;
import com.spotify.app.repository.PlaylistUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaylistUserService {

    private final PlaylistUserRepository playlistUserRepository;

    private final PlaylistResponseMapper playlistResponseMapper;


    public List<PlaylistResponse> findByUserId (Long userId) {

        List<Playlist> playlists = findPlaylistsByUser(userId);

        return playlists.stream().
                map(playlist -> playlistResponseMapper.
                        playlistToPlaylistResponseCustom(playlist,0,"",0)).
                toList() ;
    }

    @Transactional
    public void deleteByUserAndPlaylist (Long userId, Long playlistId) {
        playlistUserRepository.deleteByUserAndPlaylist(userId,playlistId);
    }

    private  List<Playlist> findPlaylistsByUser(Long userId) {
        List<PlaylistUser> playlistUserList = playlistUserRepository.findByUserid(userId);

        return playlistUserList.
                stream().
                map(PlaylistUser::getPlaylist).
                toList();
    }

    public boolean checkUserIsAddedPlaylist(Long userId, Long playlistId) {
        return playlistUserRepository.findByUserAndPlaylist(userId, playlistId).isPresent();
    }


}

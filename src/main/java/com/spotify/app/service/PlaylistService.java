package com.spotify.app.service;


import com.spotify.app.dto.PlaylistDTO;
import com.spotify.app.dto.response.PlaylistResponseDTO;
import com.spotify.app.dto.response.SongResponseDTO;
import com.spotify.app.exception.ResourceNotFoundException;
import com.spotify.app.mapper.PlaylistMapper;
import com.spotify.app.model.*;
import com.spotify.app.repository.PlaylistRepository;
import com.spotify.app.repository.PlaylistSongRepository;
import com.spotify.app.repository.PlaylistUserRepository;
import com.spotify.app.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class PlaylistService {

    private final PlaylistRepository playlistRepository ;

    private final PlaylistSongRepository playlistSongRepository ;

    private final UserRepository userRepository;

    private final SongService songService;


    private final PlaylistUserRepository playlistUserRepository;

    public List<PlaylistResponseDTO> findByUserId(Long userId) {
        return null ;
    }


    public void addUserToLikedPlaylist(Long userId, Long playlistId) {
        User user = userRepository.
                findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Playlist playlist = playlistRepository.
                findById(playlistId).
                orElseThrow(() -> new ResourceNotFoundException("Playlist not found"));

        playlist.addUser(user);
        playlistRepository.save(playlist);
    }

    @Transactional
    public void removeUserFromLikedPlaylist(Long userId, Long playlistId) {
        playlistUserRepository.deleteByUserAndPlaylist(userId,playlistId);
    }



    public PlaylistDTO findByIdWithSongs(Long playlistId) {

        // Find playlistSong by playlistId
        List<PlaylistSong> playlistSongs = playlistSongRepository.findByPlaylistId(playlistId);

        // Map List playlistSong to Song's list
        List<SongResponseDTO> songDTOS = playlistSongs
                .stream()
                .map(playlistSong -> songService.findBySong(playlistSong.getSong(),playlistSong))
                .collect(Collectors.toList());

        // Get playlist by Id
        Playlist playlist = playlistRepository.findById(playlistId).orElseThrow(() -> new ResourceNotFoundException("Playlist not found"));

        return PlaylistMapper.INSTANCE.playlistToPlaylistDTO(playlist, songDTOS);
    }



    @Transactional
    public void uploadFiles(MultipartFile image, MultipartFile thumbnail, Long albumId) {
        Playlist playlist = playlistRepository.findById(albumId).orElseThrow(() -> new ResourceNotFoundException("Playlist not found"));
        if (image != null) {
            try {
                playlist.setImage(image.getBytes());
            } catch (IOException e) {
                throw new ResourceNotFoundException(e.getMessage());
            }
        }
        if (thumbnail != null) {
            try {
                playlist.setThumbnail(thumbnail.getBytes());
            } catch (IOException e) {
                throw new ResourceNotFoundException(e.getMessage());
            }
        }
        playlistRepository.save(playlist);
    }

    public Playlist get(Long playlistId) {
        return playlistRepository.findById(playlistId).orElseThrow(() -> new ResourceNotFoundException("Playlist not found"));
    }
}

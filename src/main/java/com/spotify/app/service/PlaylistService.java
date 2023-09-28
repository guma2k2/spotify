package com.spotify.app.service;


import com.spotify.app.dto.PlaylistDTO;
import com.spotify.app.dto.SongDTO;
import com.spotify.app.dto.response.PlaylistResponseDTO;
import com.spotify.app.dto.response.SongResponseDTO;
import com.spotify.app.exception.ResourceNotFoundException;
import com.spotify.app.mapper.PlaylistMapper;
import com.spotify.app.mapper.PlaylistResponseMapper;
import com.spotify.app.mapper.SongMapper;
import com.spotify.app.mapper.SongResponseMapper;
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
    private final PlaylistMapper playlistMapper;
    private final SongService songService;
    private final UserRepository userRepository;
    private final PlaylistSongRepository playlistSongRepository;

    private final PlaylistResponseMapper playlistResponseMapper;


    private final PlaylistUserRepository playlistUserRepository;

    public List<PlaylistResponseDTO> findByUserId(Long userId) {
        List<PlaylistUser> playlistUserList = playlistUserRepository.findByUseridWithoutLikedSong(userId);

        List<Playlist> playlists = playlistUserList.stream().map(playlistUser -> playlistUser.getPlaylist()).toList();

        return playlists.
                stream().
                map(playlist -> playlistResponseMapper.
                        playlistToPlaylistResponseDTOCustom(playlist,0,0l,0l)).
                toList()  ;
    }



    public PlaylistDTO findByIdWithSongs(Long playlistId) {

        // Find playlist by id
        Playlist playlist = playlistRepository.
                findByIdReturnUserLiked(playlistId).orElseThrow(() ->
                        new ResourceNotFoundException(String.format("playlist with id [%d] not found")));

        List<PlaylistUser> playlistUserList = playlist.getPlaylistUserList();
        long likedCount = playlistUserList.size();


        // get List playlistSong by playlist
        List<PlaylistSong> playlistSongs = playlistSongRepository.findByPlaylistId(playlistId);

        int sumSongCount =  playlistSongs.stream()
                .mapToInt((playlistSong) -> playlistSong.getSong() != null ? 1 : 0).sum();

        long sumViewCount = playlistSongs.stream()
                .mapToLong((playlistSong) -> playlistSong.getSong()
                        .getViewCount())
                .sum();


        // convert playlistSongs to songResponseDTOs
        List<SongResponseDTO> songResponseDTOS = playlistSongs
                .stream()
                .map(playlistSong -> songService.findBySong(playlistSong.getSong(),playlistSong))
                .collect(Collectors.toList());

        return playlistMapper.playlistToPlaylistDTO(playlist, sumSongCount, sumViewCount,likedCount, songResponseDTOS);
    }


    public List<PlaylistResponseDTO> listAll() {
        List<Playlist> playlists = playlistRepository.findAll();
        return playlists.stream().map(playlistResponseMapper::playlistToPlaylistResponseDTO).toList();
    }



    @Transactional
    public void updatePlaylist(MultipartFile image, MultipartFile thumbnail, Long albumId,String desc, String name) {
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
        playlist.setDescription(desc);
        playlist.setName(name);
        playlistRepository.save(playlist);
    }

    @Transactional
    public void addPlaylist(MultipartFile image, MultipartFile thumbnail,String desc, String name) {
        Playlist playlist = new Playlist();
        playlist.setDescription(desc);
        playlist.setName(name);
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

    public PlaylistResponseDTO getPlaylistForAdmin(Long playlistId) {
        Playlist playlist =  playlistRepository
                .findById(playlistId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(String.format("playlist with id: [%d] not found", playlistId)));

        return playlistResponseMapper.playlistToPlaylistResponseDTOCustom(playlist,0,0l,0l);
    }

    public Playlist get(Long playlistId) {
        return playlistRepository
                .findById(playlistId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(String.format("playlist with id: [%d] not found",playlistId)));
    }


    public void addSong(Long playlistId, Long songId) {
        Song song = songService.get(songId);
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                String.format("playlist  with id: %d not found", playlistId)
                        ));

        playlist.addSong(song);
        playlistRepository.save(playlist);
    }

    public void removeSong(Long playlistId, Long songId) {
        Song song = songService.get(songId);
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                String.format("playlist  with id: %d not found", playlistId)
                        ));

        playlist.removeSong(song);
        playlistRepository.save(playlist);
    }

    public void addSongToLikedPlaylist(Long userId,Long songId) {

        PlaylistUser playlistUser = playlistUserRepository.findLikedPlaylistByUserId(userId).orElseThrow();
        Playlist playlist = playlistUser.getPlaylist();
        Song song = songService.get(songId);

        playlist.addSong(song);

        playlistRepository.save(playlist);

    }

    public void removeSongFromLikedPlaylist(Long userId,Long songId) {

        PlaylistUser playlistUser = playlistUserRepository.findLikedPlaylistByUserId(userId).orElseThrow();
        Playlist playlist = playlistUser.getPlaylist();
        Song song = songService.get(songId);

        playlist.removeSong(song);

        playlistRepository.save(playlist);
    }

    public void createPlaylistByUserId(Long userId) {
        List<PlaylistUser> playlists = playlistUserRepository.findByUseridWithoutLikedSong(userId);
        int totalOfPlaylist = playlists.size() + 1 ;
        Playlist playlist = Playlist.builder().name(String.format("My Playlist #%d",totalOfPlaylist)).build();
        playlistRepository.save(playlist);
    }
}

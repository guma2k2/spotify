package com.spotify.app.service;


import com.spotify.app.dto.PlaylistDTO;
import com.spotify.app.dto.response.PlaylistResponse;
import com.spotify.app.dto.response.SongResponse;
import com.spotify.app.exception.ResourceNotFoundException;
import com.spotify.app.mapper.PlaylistMapper;
import com.spotify.app.mapper.PlaylistResponseMapper;
import com.spotify.app.model.*;
import com.spotify.app.repository.PlaylistRepository;
import com.spotify.app.repository.PlaylistSongRepository;
import com.spotify.app.repository.PlaylistUserRepository;
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
    private final PlaylistSongRepository playlistSongRepository;
    private final PlaylistResponseMapper playlistResponseMapper;
    private final PlaylistUserRepository playlistUserRepository;
    public final String playlistNameHasAllLikedSongOfUser = "Liked Songs";




    public Playlist findByNameAndUserId(Long userId) {
        PlaylistUser playlistUser = playlistUserRepository.
                findByUserIdAndName(userId, playlistNameHasAllLikedSongOfUser).
                orElseThrow();

        return playlistUser.getPlaylist();
    }




    public Playlist get(Long playlistId) {
        return playlistRepository
                .findById(playlistId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(String.format("playlist with id: [%d] not found", playlistId)));
    }

    public List<PlaylistResponse> findByUserId(Long userId) {
        List<PlaylistUser> playlistUserList = playlistUserRepository.findByUserid(userId);

        List<Playlist> playlists = playlistUserList.stream().map(playlistUser -> playlistUser.getPlaylist()).toList();

        return playlists.
                stream().
                map(playlist -> playlistResponseMapper.
                        playlistToPlaylistResponseCustom(playlist,0,"",0l)).
                toList()  ;
    }



    public PlaylistDTO findByIdReturnSongs(Long playlistId) {

        // Find playlist by id
        Playlist playlist = playlistRepository.
                findByIdReturnPlaylistUsers(playlistId).orElseThrow(() ->
                        new ResourceNotFoundException(String.format("playlist with id [%d] not found")));

        // get playlistUserList by playlist
        List<PlaylistUser> playlistUserList = playlist.getPlaylistUserList();

        long likedCount = playlistUserList.size();

        // get list playlistSong by playlist id
        List<PlaylistSong> playlistSongs = playlistSongRepository.findByPlaylistId(playlistId);

        int sumSongCount =  findSumSongCountByPlaylistSongs(playlistSongs);

        String totalTime =  convertTotalTime(playlistSongs);


        // convert playlistSongs to songResponses
        List<SongResponse> songResponses = getsSongResponses(playlistSongs);

        return playlistMapper.playlistToPlaylistDTO(playlist, sumSongCount, totalTime, likedCount, songResponses);
    }

    private List<SongResponse> getsSongResponses(List<PlaylistSong> playlistSongs) {
        return playlistSongs
                .stream()
                .map(playlistSong -> songService.findBySong(playlistSong.getSong(), playlistSong))
                .collect(Collectors.toList());
    }

    private int findSumSongCountByPlaylistSongs(List<PlaylistSong> playlistSongs) {
        return playlistSongs.stream()
                .mapToInt((playlistSong) -> playlistSong.getSong() != null ? 1 : 0).sum();
    }





    public List<PlaylistResponse> listAll() {
        List<Playlist> playlists = playlistRepository.findAll();
        return playlists.stream().map(playlistResponseMapper::playlistToPlaylistResponse).toList();
    }



    @Transactional
    public void updatePlaylist(MultipartFile image, MultipartFile thumbnail, Long playlistId,String desc, String name) {
        Playlist underSave = get(playlistId);

        savePlaylistImage(underSave,image);
        savePlaylistThumbnail(underSave,thumbnail);
        underSave.setDescription(desc);
        underSave.setName(name);

        playlistRepository.save(underSave);
    }

    @Transactional
    public void addPlaylist(MultipartFile image, MultipartFile thumbnail,String desc, String name) {
        Playlist underSave = new Playlist();
        underSave.setDescription(desc);
        underSave.setName(name);
        savePlaylistImage(underSave,image);
        savePlaylistThumbnail(underSave,thumbnail);
        playlistRepository.save(underSave);
    }

    public PlaylistResponse getPlaylistByIdForAdmin(Long playlistId) {
        Playlist playlist =  get(playlistId);
        return playlistResponseMapper.
                playlistToPlaylistResponseCustom(playlist,0,"",0l);
    }


    public void addSong(Long playlistId, Long songId) {
        Song song = songService.get(songId);
        Playlist playlist = get(playlistId);

        playlist.addSong(song);
        playlistRepository.save(playlist);
    }

    public void removeSong(Long playlistId, Long songId) {
        Song song = songService.get(songId);

        Playlist playlist = get(playlistId);

        playlist.removeSong(song);
        playlistRepository.save(playlist);
    }

    public void addSongToLikedPlaylist(Long userId,Long songId) {
        PlaylistUser playlistUser = playlistUserRepository.
                findByUserIdAndName(userId,playlistNameHasAllLikedSongOfUser).
                orElseThrow();
        Playlist playlist = playlistUser.getPlaylist();
        Song song = songService.get(songId);

        playlist.addSong(song);

        playlistRepository.save(playlist);

    }

    public void removeSongFromLikedPlaylist(Long userId,Long songId) {
        PlaylistUser playlistUser = playlistUserRepository.
                findByUserIdAndName(userId,playlistNameHasAllLikedSongOfUser).
                orElseThrow();
        Playlist playlist = playlistUser.getPlaylist();
        Song song = songService.get(songId);

        playlist.removeSong(song);

        playlistRepository.save(playlist);
    }

    public void createPlaylistByUserId(Long userId) {
        List<PlaylistUser> playlists = playlistUserRepository.findByUseridWithoutLikedSong(userId);
        int totalOfPlaylist = playlists.size() + 1 ;
        Playlist playlist = Playlist.builder().name(String.format("My Playlist #%d", totalOfPlaylist)).build();
        playlistRepository.save(playlist);
    }

    private void savePlaylistImage(Playlist underSave, MultipartFile image) {
        if (image != null) {
            try {
                underSave.setImage(image.getBytes());
            } catch (IOException e) {
                throw new ResourceNotFoundException(e.getMessage());
            }
        }

    }

    private void savePlaylistThumbnail(Playlist underSave, MultipartFile thumbnail) {
        if (thumbnail != null) {
            try {
                underSave.setThumbnail(thumbnail.getBytes());
            } catch (IOException e) {
                throw new ResourceNotFoundException(e.getMessage());
            }
        }
    }

    private String convertTotalTime(List<PlaylistSong> playlistSongs) {

        long totalTime = playlistSongs.stream()
                .mapToLong((playlistSong) -> playlistSong.getSong()
                        .getDuration())
                .sum();
        long hours= totalTime / 3600l ;
        long minutes = (totalTime % 3600) / 60;
        long seconds = totalTime % 60;
        if(hours > 0) {
            minutes = seconds > 0l ? minutes + 1l : minutes;
            return hours + " giờ " + minutes + " phút";
        }
        return minutes + " phút " + seconds + " giây";
    }
}

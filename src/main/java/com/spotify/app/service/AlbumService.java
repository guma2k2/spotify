package com.spotify.app.service;


import com.spotify.app.dto.AlbumDTO;
import com.spotify.app.dto.SongDTO;
import com.spotify.app.dto.request.AlbumRequest;
import com.spotify.app.dto.response.AlbumResponseDTO;
import com.spotify.app.exception.ResourceNotFoundException;
import com.spotify.app.mapper.AlbumMapper;
import com.spotify.app.mapper.AlbumRequestMapper;
import com.spotify.app.mapper.AlbumResponseMapper;
import com.spotify.app.mapper.SongMapper;
import com.spotify.app.model.*;
import com.spotify.app.repository.AlbumRepository;
import com.spotify.app.repository.AlbumSongRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AlbumService {
    private final AlbumRepository albumRepository ;
    private final SongService songService;
    private final AlbumSongRepository albumSongRepository;
    private final AlbumMapper albumMapper ;
    private final UserService userService;
    private final AlbumResponseMapper albumResponseMapper;

    private final AlbumRequestMapper albumRequestMapper;
    public AlbumDTO findById(Long albumId) {

        // find album by id return their songs
        Album album = albumRepository.
                findByIdReturnSongs(albumId).
                orElseThrow(() -> new ResourceNotFoundException(String.format("album with id: [%d] not found", albumId)));

        // Find albumSong by albumId
        List<AlbumSong> albumSongs = albumSongRepository.findByAlbumId(albumId);

        int songCount = albumSongs.size();

        String totalTime =  convertTotalTime(albumSongs);

        // Map List albumSong to Song list
        List<Song> songs = albumSongs.stream().map(AlbumSong::getSong).collect(Collectors.toList());

        // Map Song to Song DTO
        List<SongDTO> songDTOS = SongMapper.INSTANCE.songsToSongsDTO(songs);

        return albumMapper.albumToAlbumDTO(album, songDTOS, songCount, totalTime);
    }

    private String convertTotalTime(List<AlbumSong> albumSongs) {
        long totalTime = albumSongs.stream()
                .mapToLong((albumSong) -> albumSong.getSong()
                        .getDuration())
                .sum();
        long hour = totalTime / 3600 ;
        long minute = totalTime / 60 + (totalTime % 3600)*60 ;
        return hour + " giờ " + minute + " phút";
    }


    @Transactional
    public void uploadFiles(MultipartFile image, MultipartFile thumbnail, Long albumId) {
        Album album = albumRepository.findById(albumId).orElseThrow(() -> new ResourceNotFoundException("Album not found"));
        if(image != null) {
            try {
                album.setImage(image.getBytes());
            } catch (IOException e) {
                throw new ResourceNotFoundException(e.getMessage());
            }
        }
        if(thumbnail != null) {
            try {
                album.setThumbnail(thumbnail.getBytes());
            } catch (IOException e) {
                throw new ResourceNotFoundException(e.getMessage());
            }
        }
        albumRepository.save(album);
    }

    public Album get(Long albumId) {
        return albumRepository.findById(albumId).orElseThrow(() -> new ResourceNotFoundException("Album not found"));
    }

    public void addSong(Long albumId, Long songId) {
        Album album = get(albumId);
        Song song = songService.get(songId);
        album.addSong(song);
        albumRepository.save(album);
    }

    public void removeSong(Long albumId, Long songId) {
        Album album = get(albumId);
        Song song = songService.get(songId);
        album.removeSong(song);
        albumRepository.save(album);
    }

    public List<AlbumResponseDTO> findAll() {
        return albumRepository.findAll().stream().map(albumResponseMapper::albumToAlbumResponseDTO).toList();
    }


    @Transactional
    public Long addAlbum(Long userId, AlbumRequest request) {
        User user = userService.get(userId);
        Album album = albumRequestMapper.dtoToEntity(request);
        album.setReleaseDate(LocalDateTime.now());
        album.setUser(user);
        Album savedAlbum = albumRepository.save(album);
        return savedAlbum.getId();
    }

    public Long updateAlbum(Long albumId, AlbumRequest request) {
        Album album = get(albumId);
        if(!request.name().equals(album.getName())){
            album.setName(request.name());
        }
        Album updatedAlbum = albumRepository.save(album);
        return updatedAlbum.getId();
    }
}

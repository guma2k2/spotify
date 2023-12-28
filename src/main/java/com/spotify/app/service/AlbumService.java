package com.spotify.app.service;


import com.spotify.app.dto.AlbumDTO;
import com.spotify.app.dto.request.AlbumRequest;
import com.spotify.app.dto.response.AlbumResponse;
import com.spotify.app.dto.response.PlaylistResponse;
import com.spotify.app.dto.response.SongResponse;
import com.spotify.app.exception.ResourceNotFoundException;
import com.spotify.app.mapper.*;
import com.spotify.app.model.*;
import com.spotify.app.repository.AlbumRepository;
import com.spotify.app.repository.AlbumSongRepository;
import com.spotify.app.repository.SongRepository;
import com.spotify.app.repository.UserRepository;
import com.spotify.app.utility.FileUploadUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AlbumService {
    private final AlbumRepository albumRepository ;
    private final SongRepository songRepository ;
    private final AlbumSongRepository albumSongRepository;
    private final AlbumMapper albumMapper ;
    private final UserRepository userRepository;
    private final AlbumResponseMapper albumResponseMapper;
    private final AlbumRequestMapper albumRequestMapper;
    private final SongResponseMapper songResponseMapper;
    private final CloudinaryService cloudinaryService;


    public AlbumDTO findById(Long albumId) {
        // find album by id return their songs
        Album album = albumRepository.
                findByIdReturnSongs(albumId).
                orElseThrow(() -> new ResourceNotFoundException(String.format("album with id: [%d] not found", albumId)));

        // Find albumSong by albumId
        List<AlbumSong> albumSongs = albumSongRepository.findByAlbumId(albumId);

        int songCount = albumSongs.size();

        String totalTime =  this.convertTotalTime(albumSongs);

        // Map List albumSong to Song list

        List<Song> songs = null;
        List<SongResponse> songResponses = null;
        if (albumSongs.size() != 0) {
            songs = albumSongs.stream().map(AlbumSong::getSong).filter(Song::isStatus).toList();

            // Map Song to Song DTO
            songResponses = songs.stream().map(song -> songResponseMapper.songToSongResponse(song,null,null,null)).toList();
        }
        return albumMapper.albumToAlbumDTO(album, songResponses, songCount, totalTime);
    }

    public void saveAlbumImage( MultipartFile image, Long albumId) {
        Album underSave = get(albumId);
        if (!image.isEmpty()) {
            String fileDir = String.format("album-images/%d/", albumId);

            if(underSave.getImage() != null) {
                String currentFileDir = underSave.getImage();
                cloudinaryService.destroyFile(currentFileDir);
            }
            String newFileDir = fileDir + StringUtils.cleanPath(image.getOriginalFilename());
            String newPath = cloudinaryService.uploadFile(image, newFileDir);
            underSave.setImage(newPath);
            albumRepository.save(underSave);
        }
    }

    public void saveAlbumThumbnail( MultipartFile thumbnail, Long albumId) {
        Album underSave = get(albumId);
        if (!thumbnail.isEmpty()) {
            String fileDir = String.format("album-thumbnails/%d/", albumId);

            if(underSave.getThumbnail() != null) {
                String currentFileDir = underSave.getThumbnail();
                cloudinaryService.destroyFile(currentFileDir);
            }
            String newFileDir = fileDir + StringUtils.cleanPath(thumbnail.getOriginalFilename());
            String newPath = cloudinaryService.uploadFile(thumbnail, newFileDir);
            underSave.setThumbnail(newPath);
            albumRepository.save(underSave);
        }
    }

    public Album get(Long albumId) {
        return albumRepository.
                findById(albumId).
                orElseThrow(() ->
                        new ResourceNotFoundException(String.format("album with id [%d] not found", albumId)));
    }


    public void addSong(Long albumId, Long songId) {
        Album album = albumRepository.
                findById(albumId).
                orElseThrow(() ->
                        new ResourceNotFoundException(String.format("album with id [%d] not found", albumId)));
        Song song = songRepository.
                findById(songId).
                orElseThrow(() ->
                        new ResourceNotFoundException(String.format("song with id %d not found", songId))) ;
        album.addSong(song);
        albumRepository.save(album);
    }

    public void removeSong(Long albumId, Long songId) {
        Album album = albumRepository.
                findById(albumId).
                orElseThrow(() ->
                        new ResourceNotFoundException(String.format("album with id [%d] not found", albumId)));
        Song song = songRepository.
                findById(songId).
                orElseThrow(() ->
                        new ResourceNotFoundException(String.format("song with id %d not found", songId))) ;
        album.removeSong(song);
        albumRepository.save(album);
    }

    public List<AlbumResponse> findAll() {
        return albumRepository.findAll().stream().map(albumResponseMapper::albumToAlbumResponse).toList();
    }


    public Long addAlbum(Long userId, AlbumRequest request) {
        User user = userRepository.
                findById(userId).
                orElseThrow(() -> new ResourceNotFoundException(String.format("user %d not found", userId)));
        Album album = albumRequestMapper.dtoToEntity(request);
        album.setReleaseDate(LocalDateTime.now());
        album.setUser(user);
        Album savedAlbum = albumRepository.save(album);
        return savedAlbum.getId();
    }

    public void updateAlbum(Long albumId, AlbumRequest request) {
        Album album = albumRepository.
                findById(albumId).
                orElseThrow(() ->
                        new ResourceNotFoundException(String.format("album with id [%d] not found", albumId)));

        if(!request.name().equals(album.getName())){
            album.setName(request.name());
        }
        albumRepository.save(album);
    }

    public List<AlbumResponse> findAlbumByUserId(Long userId) {
        List<Album> albums = albumRepository.findByUserId(userId);
        return albums.stream().map(albumResponseMapper::albumToAlbumResponse).toList();
    }


    public String convertTotalTime(List<AlbumSong> albumSongs) {
        long totalTime = albumSongs.stream()
                .mapToLong((albumSong) -> albumSong.getSong()
                        .getDuration())
                .sum();
        long hours= totalTime / 3600l ;
        long minutes = (totalTime % 3600) / 60;
        long seconds = totalTime % 60;
        minutes = seconds > 0l ? minutes + 1l : minutes;
        if(hours > 0) {
            return hours + " giờ " + minutes + " phút";
        }
        return minutes + " phút " + seconds + " giây";
    }

    public List<AlbumResponse> findAllByName(String name) {
        Sort sort = Sort.by("id").ascending();
        Pageable pageable = PageRequest.of(0,5,sort);
        Page<Album> albums = albumRepository.findAllByName(name, pageable);
        return albumResponseMapper.albumsToAlbumsResponse(albums.getContent());
    }

    public AlbumResponse findByIdByAdmin(Long albumId) {
        Album album = albumRepository.findByIdReturnUser(albumId).orElseThrow(() ->
                new ResourceNotFoundException(String.format("album with id [%d] not found",albumId)));
        return albumResponseMapper.albumToAlbumResponse(album);
    }

    public String updateStatusAlbum(Long albumId) {
        Album album = albumRepository.
                findById(albumId).
                orElseThrow(() ->
                        new ResourceNotFoundException(String.format("album with id [%d] not found", albumId)));
        album.setStatus(!album.isStatus());
        albumRepository.saveAndFlush(album);
        String status = !album.isStatus() ? "enabled" : "disabled";
        return String.format("album with id: %d is ".concat(status),albumId);
    }

}

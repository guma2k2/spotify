package com.spotify.app.service;


import com.spotify.app.dto.AlbumDTO;
import com.spotify.app.dto.request.AlbumRequest;
import com.spotify.app.dto.response.AlbumResponse;
import com.spotify.app.dto.response.SongResponse;
import com.spotify.app.exception.ResourceNotFoundException;
import com.spotify.app.mapper.*;
import com.spotify.app.model.*;
import com.spotify.app.repository.AlbumRepository;
import com.spotify.app.repository.AlbumSongRepository;
import com.spotify.app.repository.SongRepository;
import com.spotify.app.repository.UserRepository;
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
    private final SongRepository songRepository ;
    private final AlbumSongRepository albumSongRepository;
    private final AlbumMapper albumMapper ;
    private final UserRepository userRepository;
    private final AlbumResponseMapper albumResponseMapper;
    private final AlbumRequestMapper albumRequestMapper;
    private final S3Service s3Service;
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
        List<SongResponse> songResponses = songs.stream().map(song -> SongResponseMapper.INSTANCE.songToSongResponse(song,null,null)).toList();

        return albumMapper.albumToAlbumDTO(album, songResponses, songCount, totalTime);
    }




    @Transactional
    public void uploadFiles(MultipartFile image, MultipartFile thumbnail, Long albumId) {
        Album underSave = get(albumId);

        saveAlbumImage(image,albumId);

        saveAlbumThumbnail(thumbnail, albumId);

        albumRepository.save(underSave);
    }

    public void saveAlbumImage( MultipartFile image, Long albumId) {
        Album underSave = get(albumId);
        if(image != null) {
            underSave.setImage(image.getOriginalFilename());
            try {
                if(!underSave.getImage().isEmpty()) {
                    s3Service.removeObject(String.format("album/image/%d/%s",underSave.getId(),image.getOriginalFilename()));
                }
                s3Service.putObject(
                        String.format("album/image/%d/%s",underSave.getId(),image.getOriginalFilename()),image.getBytes());
                albumRepository.save(underSave);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    public void saveAlbumThumbnail( MultipartFile thumbnail, Long albumId) {
        Album underSave = get(albumId);
        if(thumbnail != null) {
            underSave.setThumbnail(thumbnail.getOriginalFilename());
            try {
                if(!underSave.getThumbnail().isEmpty()) {
                    s3Service.removeObject(String.format("album/thumbnail/%d/%s",underSave.getId(),thumbnail.getOriginalFilename()));
                }
                s3Service.putObject(
                        String.format("album/thumbnail/%d/%s",underSave.getId(),thumbnail.getOriginalFilename()),thumbnail.getBytes());
                albumRepository.save(underSave);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }

    }

    public byte[] getAlbumImage(Long albumId) {
        Album underGet = get(albumId);
        if (underGet.getImage().isEmpty()) {
            throw new ResourceNotFoundException(
                    "album id :[%d] not found thumbnail".formatted(albumId));
        }

        byte[] albumImage = s3Service.getObject(
                "album/image/%d/%s".formatted(albumId, underGet.getImage())
        );
        return albumImage;
    }

    public byte[] getAlbumThumbnail(Long albumId) {
        Album underGet = get(albumId);
        if (underGet.getThumbnail().isEmpty()) {
            throw new ResourceNotFoundException(
                    "album id :[%d] not found thumbnail".formatted(albumId));
        }

        byte[] albumThumbnail = s3Service.getObject(
                "album/thumbnail/%d/%s".formatted(albumId, underGet.getThumbnail())
        );
        return albumThumbnail;
    }

    public Album get(Long albumId) {
        return albumRepository.
                findById(albumId).
                orElseThrow(() ->
                        new ResourceNotFoundException(String.format("album with id [%d] not found",albumId)));
    }


    @Transactional
    public void addSong(Long albumId, Long songId) {
        Album album = get(albumId);
        Song song = getSongBySongId(songId);
        album.addSong(song);
        albumRepository.save(album);
    }

    @Transactional
    public void removeSong(Long albumId, Long songId) {
        Album album = get(albumId);
        Song song = getSongBySongId(songId);
        album.removeSong(song);
        albumRepository.save(album);
    }

    public List<AlbumResponse> findAll() {
        return albumRepository.findAll().stream().map(albumResponseMapper::albumToAlbumResponse).toList();
    }


    @Transactional
    public Long addAlbum(Long userId, AlbumRequest request) {
        User user = getUserByUserId(userId);
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

    public List<AlbumResponse> findAlbumByUserId(Long userId) {
        List<Album> albums = albumRepository.findByUserId(userId);
        return albums.stream().map(albumResponseMapper::albumToAlbumResponse).toList();
    }

    public User getUserByUserId(Long userId) {
        return userRepository.
                findById(userId).
                orElseThrow(() -> new ResourceNotFoundException(String.format("user %d not found", userId)));
    }


    public Song getSongBySongId(Long songId) {
        return songRepository.
                findById(songId).
                orElseThrow(() ->
                        new ResourceNotFoundException(String.format("song with id %d not found",songId))) ;
    }

    private String convertTotalTime(List<AlbumSong> albumSongs) {
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




}

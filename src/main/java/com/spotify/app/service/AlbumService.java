package com.spotify.app.service;


import com.spotify.app.dto.AlbumDTO;
import com.spotify.app.dto.SongDTO;
import com.spotify.app.exception.UserException;
import com.spotify.app.mapper.AlbumMapper;
import com.spotify.app.mapper.SongMapper;
import com.spotify.app.model.*;
import com.spotify.app.repository.AlbumRepository;
import com.spotify.app.repository.AlbumSongRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AlbumService {
    private final AlbumRepository albumRepository ;
    private final AlbumSongRepository albumSongRepository;
    public AlbumDTO findById(Long albumId) {

        // Find albumSong by albumId
        List<AlbumSong> albumSongs = albumSongRepository.findByAlbumId(albumId);

        // Map List albumSong to Song's list
        List<Song> songs = albumSongs.stream().map(AlbumSong::getSong).collect(Collectors.toList());

        // Map Song to Song DTO
        List<SongDTO> songDTOS = SongMapper.INSTANCE.songsToSongsDTO(songs);

        // Get album by id
        Album album = albumRepository.findByIdCustom(albumId).orElseThrow(() -> new UserException("Album not found"));

        return AlbumMapper.INSTANCE.albumToAlbumDTO(album, songDTOS);
    }

}

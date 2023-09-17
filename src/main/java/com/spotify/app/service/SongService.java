package com.spotify.app.service;
import com.spotify.app.dto.response.AlbumResponseDTO;
import com.spotify.app.dto.response.SongResponseDTO;
import com.spotify.app.exception.UserException;
import com.spotify.app.mapper.AlbumResponseMapper;
import com.spotify.app.mapper.SongResponseMapper;
import com.spotify.app.model.Album;
import com.spotify.app.model.AlbumSong;
import com.spotify.app.model.PlaylistSong;
import com.spotify.app.model.Song;
import com.spotify.app.repository.AlbumSongRepository;
import com.spotify.app.repository.SongRepository;
import com.spotify.app.utility.FileUploadUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SongService {

    private final SongRepository songRepository ;

    private final AlbumSongRepository albumSongRepository;

    public Song get(Long songId) {
        return songRepository.findById(songId).orElseThrow(() -> new UserException("Song not found")) ;
    }

    public SongResponseDTO getById(Long songId) {
        Song song = songRepository.findByIdCustom(songId).orElseThrow() ;
        return SongResponseMapper.INSTANCE.songToSongResponseDTO(song,null,null);
    }


    public void saveSongAudio(MultipartFile multipartFile, Long songId) throws IOException {
        Song song = this.get(songId);

        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            song.setAudio(fileName);

            String uploadDir = "song-audios/" + songId;

            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        } else {
            if (song.getAudio().isEmpty()) song.setAudio(null);
        }
        songRepository.save(song);
    }
    public void saveSongImage(MultipartFile image, Long songId) {
        Song song = songRepository.findById(songId).orElseThrow(() -> new UserException("Song not found"));
        if(image != null) {
            try {
                song.setImage(image.getBytes());
            } catch (IOException e) {
                throw new UserException(e.getMessage());
            }
        }
        songRepository.save(song);
    }


    public SongResponseDTO findBySong(Song song, PlaylistSong playlistSong) {

        List<AlbumSong> albumSongs = albumSongRepository.findBySongId(song.getId());

        List<Album> albums = albumSongs.stream().map(AlbumSong::getAlbum).collect(Collectors.toList());

        List<AlbumResponseDTO> albumResponseDTOS = AlbumResponseMapper.INSTANCE.albumsToAlbumsResponseDTO(albums);

        return SongResponseMapper.INSTANCE.songToSongResponseDTO(song, albumResponseDTOS, playlistSong.getCreatedOn());
    }



}

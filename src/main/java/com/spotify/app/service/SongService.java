package com.spotify.app.service;
import com.spotify.app.dto.SongDTO;
import com.spotify.app.enums.Genre;
import com.spotify.app.mapper.SongMapper;
import com.spotify.app.model.*;
import com.spotify.app.repository.PlaylistSongRepository;
import com.spotify.app.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import com.spotify.app.dto.response.AlbumResponseDTO;
import com.spotify.app.dto.response.SongResponseDTO;
import com.spotify.app.exception.ResourceNotFoundException;
import com.spotify.app.mapper.AlbumResponseMapper;
import com.spotify.app.mapper.SongResponseMapper;
import com.spotify.app.repository.AlbumSongRepository;
import com.spotify.app.repository.SongRepository;
import com.spotify.app.utility.FileUploadUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.sound.sampled.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
@Service
@AllArgsConstructor
@Slf4j
public class SongService {

    private final SongRepository songRepository ;

    private final AlbumSongRepository albumSongRepository;

    private final PlaylistSongRepository playlistSongRepository;

    private final SongResponseMapper songResponseMapper;
    private final SongMapper songMapper;
    private final UserService userService;

    private final UserRepository userRepository ;
    public Song get(Long songId) {
        return songRepository.findById(songId).orElseThrow(() -> new ResourceNotFoundException("Song not found")) ;
    }

    public SongResponseDTO getById(Long songId) {
        Song song = songRepository.findByIdCustom(songId).orElseThrow() ;
        return songResponseMapper.songToSongResponseDTO(song,null,null);
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
        Song song = songRepository.
                findById(songId).
                orElseThrow(() ->
                        new ResourceNotFoundException(String.format("song with id: [%d] not found",songId)));

        if(image != null) {
            try {
                song.setImage(image.getBytes());
            } catch (IOException e) {
                throw new ResourceNotFoundException(e.getMessage());
            }
        }
        songRepository.save(song);
    }


    public SongResponseDTO findBySong(Song song, PlaylistSong playlistSong) {

        List<AlbumSong> albumSongs = albumSongRepository.findBySongId(song.getId());

        List<Album> albums = albumSongs.stream().map(AlbumSong::getAlbum).collect(Collectors.toList());

        List<AlbumResponseDTO> albumResponseDTOS = AlbumResponseMapper.INSTANCE.albumsToAlbumsResponseDTO(albums);

        String pattern = "dd/MM/yyyy hh:mm:ss";
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(pattern);

        return songResponseMapper.songToSongResponseDTO(song, albumResponseDTOS, playlistSong.getCreatedOn().format(dateFormat));
    }

    public List<SongDTO> findByPlaylistId(Long playlistId) {
        List<PlaylistSong> playlistSongs =  playlistSongRepository.findByPlaylistId(playlistId);
        List<Song> songs = playlistSongs.stream().map(PlaylistSong::getSong).toList();
        return songMapper.songsToSongsDTO(songs);
    }

    public List<SongDTO> findAll() {
        return songMapper.songsToSongsDTO(songRepository.findAll());
    }


    public void addSong(MultipartFile image, MultipartFile audio, String lyric, String genre, String name, int duration,Long userId) throws IOException {
        // Todo: check exit by name

        if(checkSongExitByName(name)) {
            throw new ResourceNotFoundException(String.format("song with name: [%s] not found",name));
        }

        User user = userService.get(userId);

        Song song = new Song();
        if(image != null) {
            try {
                song.setImage(image.getBytes());
            } catch (IOException e) {
                throw new ResourceNotFoundException(e.getMessage());
            }
        }

        song.setLyric(lyric);
        song.setGenre(Genre.valueOf(genre));
        song.setName(name);
        song.setDuration(duration);
        song.setReleaseDate(LocalDateTime.now());
        Song savedSong = songRepository.save(song);
        if (!audio.isEmpty()) {
            String fileName = StringUtils.cleanPath(audio.getOriginalFilename());
            song.setAudio(fileName);

            String uploadDir = "song-audios/" + savedSong.getId();
            FileUploadUtil.cleanDir(uploadDir);
            try {
                FileUploadUtil.saveFile(uploadDir, fileName, audio);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        } else {
            if (song.getAudio().isEmpty()) song.setAudio(null);
        }
        user.addSong(song);
        userRepository.save(user);
    }

    private boolean checkSongExitByName(String name) {
        return songRepository.findByName(name).isPresent();
    }

    public List<SongResponseDTO> findByNameFullText(String name) {
        List<Song> songs = songRepository.findByNameFullText(name);
        List<SongResponseDTO> songResponseDTOS = songs
                .stream()
                .map(song ->
                        songResponseMapper.songToSongResponseDTO(song,null,null))
                .toList();
        return songResponseDTOS;
    }
}

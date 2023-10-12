package com.spotify.app.service;
import com.spotify.app.dto.SongDTO;
import com.spotify.app.dto.request.SongRequest;
import com.spotify.app.dto.response.SongResponse;
import com.spotify.app.enums.Genre;
import com.spotify.app.mapper.SongMapper;
import com.spotify.app.model.*;
import com.spotify.app.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import com.spotify.app.dto.response.AlbumResponse;
import com.spotify.app.exception.ResourceNotFoundException;
import com.spotify.app.mapper.AlbumResponseMapper;
import com.spotify.app.mapper.SongResponseMapper;
import com.spotify.app.utility.FileUploadUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    private final AlbumResponseMapper albumResponseMapper;
    private final UserRepository userRepository ;
    private final AlbumRepository albumRepository;
    private final S3Service s3Service;

    public Song get(Long songId) {
        return songRepository.findById(songId).orElseThrow(() -> new ResourceNotFoundException("Song not found")) ;
    }

    public SongResponse getById(Long songId) {
        Song song = songRepository.findByIdCustom(songId).orElseThrow() ;

        if(song.getAlbumSongList().isEmpty()) {
            return songResponseMapper.songToSongResponse(song,null,null);
        }
        List<Album> albums = albumSongRepository.findBySongId(songId).stream().map(AlbumSong::getAlbum).toList();
        List<AlbumResponse> albumResponses = albumResponseMapper.albumsToAlbumsResponse(albums);
        return songResponseMapper.songToSongResponse(song, albumResponses,null);
    }


    public void saveSongAudio(MultipartFile audio, Long songId) throws IOException {
        Song song = get(songId);

        if(audio != null) {
            song.setAudio(audio.getOriginalFilename());
            try {
                s3Service.putObject(String.format("song/audio/%d/%s",song.getId(),audio.getOriginalFilename()),audio.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        songRepository.save(song);
    }
    public void saveSongImage(MultipartFile image, Long songId) {
        Song song = get(songId);
        if(image != null) {
            song.setImage(image.getOriginalFilename());
            try {
                if(!song.getImage().isEmpty()) {
                    s3Service.removeObject(String.format("song/image/%d/%s",song.getId(),image.getOriginalFilename()));
                }
                s3Service.putObject(String.format("song/image/%d/%s",song.getId(),image.getOriginalFilename()),image.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        songRepository.save(song);
    }
    public byte[] getSongImage(Long songId) {
        Song underGet = get(songId);
        if (underGet.getImage().isEmpty()) {
            throw new ResourceNotFoundException(
                    "song with id image :[%d] not found".formatted(songId));
        }

        byte[] songImage = s3Service.getObject(
                "song/image/%d/%s".formatted(songId, underGet.getImage())
        );
        return songImage;
    }

    public byte[] getSongAudio(Long songId) {
        Song underGet = get(songId);
        if (underGet.getImage().isEmpty()) {
            throw new ResourceNotFoundException(
                    "song with id audio :[%d] not found".formatted(songId));
        }

        byte[] songAudio = s3Service.getObject(
                "song/audio/%d/%s".formatted(songId, underGet.getAudio())
        );
        return songAudio;
    }

    public SongResponse findBySong(Song song, PlaylistSong playlistSong) {

        List<AlbumSong> albumSongs = albumSongRepository.findBySongId(song.getId());

        List<Album> albums = albumSongs.stream().map(AlbumSong::getAlbum).collect(Collectors.toList());

        List<AlbumResponse> albumResponses = albumResponseMapper.albumsToAlbumsResponse(albums);

        String createdOn = formattedCreatedOnWithCorrectPattern(playlistSong);

        return songResponseMapper.songToSongResponse(song, albumResponses, createdOn);
    }

    public List<SongDTO> findByPlaylistId(Long playlistId) {
        List<PlaylistSong> playlistSongs =  playlistSongRepository.findByPlaylistId(playlistId);
        List<Song> songs = playlistSongs.stream().map(PlaylistSong::getSong).toList();
        return songMapper.songsToSongsDTO(songs);
    }

    public List<SongDTO> findAll() {
        return songMapper.songsToSongsDTO(songRepository.findAll());
    }


    public void addSong(MultipartFile image,
                        String lyric,
                        String genre,
                        String name,
                        int duration,
                        Long userId) throws IOException {

        // Todo: check exit by name
        if(checkSongExitByName(name.trim())) {
            throw new ResourceNotFoundException(String.format("song with name: [%s] not found",name));
        }

        User user = getUserByUserId(userId);

        Song underSave = new Song();

        saveSongImage(image,underSave.getId());

        underSave.setLyric(lyric);
        underSave.setGenre(Genre.valueOf(genre));
        underSave.setName(name);
        underSave.setDuration(duration);
        underSave.setReleaseDate(LocalDateTime.now());

        Song savedSong = songRepository.save(underSave);

        Album album = triggerCreateSingleAlbumWhenSaveSong(savedSong);

        user.addAlbum(album);
        user.addSong(underSave);
        album.addSong(underSave);
        userRepository.save(user);

    }

    private boolean checkSongExitByName(String name) {
        return songRepository.findByName(name).isPresent();
    }
    public List<SongResponse> findByNameFullText(String name) {
        List<Song> songs = songRepository.findByNameFullText(name);
        List<SongResponse> songResponses = songs
                .stream()
                .map(song ->
                        songResponseMapper.songToSongResponse(song,null,null))
                .toList();
        return songResponses;
    }
    public User getUserByUserId(Long userId) {
        return userRepository.
                findById(userId).
                orElseThrow(() ->
                        new ResourceNotFoundException(String.format("user %d not found", userId)));
    }
    private String formattedCreatedOnWithCorrectPattern(PlaylistSong playlistSong) {
        String pattern = "dd/MM/yyyy hh:mm:ss";
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(pattern);
        return playlistSong.getCreatedOn().format(dateFormat);
    }


    public Album triggerCreateSingleAlbumWhenSaveSong(Song song) {
        Album album = Album.builder()
                .name(song.getName())
                .image(song.getImage())
                .releaseDate(LocalDateTime.now())
                .build();
        return albumRepository.save(album);
    }

    public void saveSong(SongRequest request) {
        if(checkSongExitByName(request.name().trim())) {
            throw new ResourceNotFoundException(String.format("song with name: [%s] not found",request.name()));
        }

        List<User> users = new ArrayList<>();
        for(Long userId : request.usersId()){
            User user = getUserByUserId(userId);
            users.add(user);
        }

        Song underSave = new Song();

        underSave.setLyric(request.lyric());
        underSave.setGenre(Genre.valueOf(request.genre()));
        underSave.setName(request.name());
        underSave.setDuration(request.duration());
        underSave.setReleaseDate(LocalDateTime.now());

        Song savedSong = songRepository.save(underSave);

        Album album = triggerCreateSingleAlbumWhenSaveSong(savedSong);

        users.get(0).addAlbum(album);

        users.forEach(user -> user.addSong(underSave));
        album.addSong(underSave);
        userRepository.saveAll(users);
    }
}

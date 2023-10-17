package com.spotify.app.service;
import com.spotify.app.dto.SongDTO;
import com.spotify.app.dto.request.SongRequest;
import com.spotify.app.dto.response.SongResponse;
import com.spotify.app.enums.Genre;
import com.spotify.app.exception.DuplicateResourceException;
import com.spotify.app.mapper.SongMapper;
import com.spotify.app.model.*;
import com.spotify.app.repository.*;
import jakarta.transaction.Transactional;
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

    public Song get(Long songId) {
        return songRepository.findById(songId).orElseThrow(() -> new ResourceNotFoundException("Song not found")) ;
    }

    public SongResponse getById(Long songId) {
        Song song = songRepository.findByIdReturnUsersAlbums(songId).orElseThrow() ;

        if(song.getAlbumSongList().isEmpty()) {
            return songResponseMapper.songToSongResponse(song,null,null);
        }
        List<Album> albums = albumSongRepository.findBySongId(songId).stream().map(AlbumSong::getAlbum).toList();
        List<AlbumResponse> albumResponses = albumResponseMapper.albumsToAlbumsResponse(albums);
        return songResponseMapper.songToSongResponse(song, albumResponses,null);
    }


    public void saveSongAudio(MultipartFile multipartFile, Long songId) throws IOException {
        Song song = get(songId);

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
        Song song = get(songId);

        if(image != null) {
            try {
                song.setImage(image.getBytes());
            } catch (IOException e) {
                throw new ResourceNotFoundException(e.getMessage());
            }
        }
        songRepository.save(song);
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
        underSave.setName(request.name());
        underSave.setLyric(request.lyric());
        underSave.setGenre(Genre.valueOf(request.genre()));
        underSave.setDuration(request.duration());
        underSave.setReleaseDate(LocalDateTime.now());

        Song savedSong = songRepository.save(underSave);

        Album album = triggerCreateSingleAlbumWhenSaveSong(savedSong);

        users.get(0).addAlbum(album);

        users.forEach(user -> user.addSong(underSave));
        album.addSong(underSave);
        userRepository.saveAll(users);
    }

    public void updateSong(SongRequest request, Long songId) {
        Song underUpdate = get(songId);
        if(checkSongExitByName(request.name().trim()) && !underUpdate.getName().equals(request.name())) {
            throw new DuplicateResourceException(String.format("song with name: [%s] exited",request.name()));
        }
        underUpdate.setName(request.name());
        underUpdate.setLyric(request.lyric());
        underUpdate.setGenre(Genre.valueOf(request.genre()));
        underUpdate.setDuration(request.duration());
        songRepository.save(underUpdate);
    }


    @Transactional
    public void updateStatus(Long songId){
        Song underUpdate = get(songId);
        songRepository.updateStatus(songId,!underUpdate.isStatus());
    }


}

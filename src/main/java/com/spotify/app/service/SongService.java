package com.spotify.app.service;
import com.spotify.app.dto.SongDTO;
import com.spotify.app.dto.request.SongRequest;
import com.spotify.app.dto.response.ReviewResponse;
import com.spotify.app.dto.response.SongResponse;
import com.spotify.app.dto.response.SongSearchResponse;
import com.spotify.app.enums.Genre;
import com.spotify.app.exception.DuplicateResourceException;
import com.spotify.app.mapper.*;
import com.spotify.app.model.*;
import com.spotify.app.repository.*;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import com.spotify.app.dto.response.AlbumResponse;
import com.spotify.app.exception.ResourceNotFoundException;
import com.spotify.app.utility.FileUploadUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
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
    private final ReviewService reviewService ;
    private final RestTemplate restTemplate;
    private final SongSearchResponseMapper songSearchResponseMapper;
    //    private final S3Service s3Service;


    public Song get(Long songId) {
        return songRepository.findById(songId).orElseThrow(() ->
                new ResourceNotFoundException(String.format("song with id:%d not found",songId)));
    }

    public SongResponse getById(Long songId) {
        Song song = songRepository.findByIdReturnUsersAlbumsReviews(songId).orElseThrow() ;

        if(song.getAlbumSongList().isEmpty()) {
            return songResponseMapper.songToSongResponse(song,null,null,null);
        }
        List<Album> albums = albumSongRepository.findBySongId(songId).stream().map(AlbumSong::getAlbum).toList();
        List<AlbumResponse> albumResponses = albumResponseMapper.albumsToAlbumsResponse(albums);
        List<ReviewResponse> reviewResponses = reviewService.findBySongId(songId);
        return songResponseMapper.songToSongResponse(song, albumResponses,reviewResponses,null);
    }


    public SongResponse saveSongAudio(MultipartFile audio, Long songId) {
        Song song = get(songId);
        if (!audio.isEmpty()) {
            String fileName = StringUtils.cleanPath(audio.getOriginalFilename());
            song.setAudio(fileName);
            String uploadDir = "song-audios/" + songId;
            FileUploadUtil.cleanDir(uploadDir);
            try {
                FileUploadUtil.saveFile(uploadDir, fileName, audio);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        } else {
            if (song.getAudio().isEmpty()){
                song.setAudio(null);
            }
        }
        songRepository.save(song);
        return getById(songId);
    }
    public SongResponse saveSongImage(MultipartFile image, Long songId) {
        Song song = get(songId);
        if (!image.isEmpty()) {
            String fileName = StringUtils.cleanPath(image.getOriginalFilename());
            song.setImage(fileName);
            String uploadDir = "song-images/" + songId;
            FileUploadUtil.cleanDir(uploadDir);
            try {
                FileUploadUtil.saveFile(uploadDir, fileName, image);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        } else {
            if (song.getImage().isEmpty()){
                song.setImage(null);
            }
        }
        songRepository.save(song);
        return getById(songId);
    }
    public SongResponse findBySong(Song song, PlaylistSong playlistSong) {

        List<AlbumSong> albumSongs = albumSongRepository.findBySongId(song.getId());

        List<Album> albums = albumSongs.stream().map(AlbumSong::getAlbum).collect(Collectors.toList());

        List<AlbumResponse> albumResponses = albumResponseMapper.albumsToAlbumsResponse(albums);

        String createdOn = formattedCreatedOnWithCorrectPattern(playlistSong);

        return songResponseMapper.songToSongResponse(song, albumResponses, null, createdOn);
    }

    public List<SongDTO> findByPlaylistId(Long playlistId) {
        List<PlaylistSong> playlistSongs =  playlistSongRepository.findByPlaylistId(playlistId);
        List<Song> songs = playlistSongs.stream().map(PlaylistSong::getSong).toList();
        return songMapper.songsToSongsDTO(songs);
    }

    public List<SongDTO> findAll() {
        return songMapper.songsToSongsDTO(songRepository.findAll());
    }
    public boolean checkSongExitByName(String name) {
        return songRepository.findByName(name).isPresent();
    }
    public List<SongSearchResponse> findByNameFullText(String name) {
        Sort sort = Sort.by("id").ascending();
        Pageable pageable = PageRequest.of(0,5,sort);
        Page<Song> songPage = songRepository.findByNameFullText(name,pageable);
        List<Song> songs = songPage.getContent();
        return songSearchResponseMapper.songsToSongsDTO(songs);
    }

    private SongResponse songToSongResponse(Song song) {
        if(song.getAlbumSongList().isEmpty()) {
            return songResponseMapper.songToSongResponse(song,null,null,null);
        }
        List<Album> albums = albumSongRepository.
                findBySongId(song.getId()).stream().map(AlbumSong::getAlbum).toList();

        List<AlbumResponse> albumResponses = albumResponseMapper.albumsToAlbumsResponse(albums);

        return  songResponseMapper.songToSongResponse(song,albumResponses,null,null);
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
    public SongResponse saveSong(SongRequest request) {
        if(checkSongExitByName(request.name().trim())) {
            throw new DuplicateResourceException(String.format("song with name: [%s] existed",request.name()));
        }
        User user = getUserByUserId(request.usersId());

        Song underSave = new Song();
        underSave.setName(request.name());
        underSave.setLyric(request.lyric());
        underSave.setLabel(request.label());
        underSave.setGenre(Genre.valueOf(request.genre()));
        underSave.setName(request.name());
        underSave.setDuration(request.duration());
        underSave.setReleaseDate(LocalDateTime.of(request.year(),request.month(),request.day(),0,0));
        Song savedSong = songRepository.save(underSave);

        Album album = triggerCreateSingleAlbumWhenSaveSong(savedSong);

        user.addAlbum(album);

        user.addSong(underSave);
        album.addSong(underSave);
        userRepository.saveAndFlush(user);
        return getById(savedSong.getId());
    }
    public SongResponse updateSong(SongRequest request, Long songId) {
        Song underUpdate = get(songId);
        if(checkSongExitByName(request.name().trim()) && !underUpdate.getName().equals(request.name())) {
            throw new DuplicateResourceException(String.format("song with name: [%s] exited",request.name()));
        }
        underUpdate.setName(request.name());
        underUpdate.setLabel(request.label());
        underUpdate.setName(request.name());
        underUpdate.setLyric(request.lyric());
        underUpdate.setGenre(Genre.valueOf(request.genre()));
        underUpdate.setDuration(request.duration());
        underUpdate.setReleaseDate(LocalDateTime.of(request.year(),request.month(),request.day(),0,0));
        return getById(songRepository.save(underUpdate).getId());
    }
    public SongResponse addUser(Long songId, Long userId) {
        Song song = songRepository.findByIdReturnUsersAlbumsReviews(songId).orElseThrow();
        Set<User> userSet = song.getUsers();
        User user = getUserByUserId(userId);
        if(!userSet.contains(user)){
            user.addSong(song);
            userRepository.saveAndFlush(user);
        }
        return getById(songId);
    }

    public SongResponse removeUser(Long songId, Long userId) {
        Song song = songRepository.findByIdReturnUsersAlbumsReviews(songId).orElseThrow();
        Set<User> userSet = song.getUsers();
        User user = userRepository.
                findByIdReturnRoleAndSongs(userId).
                orElseThrow(() ->
                        new ResourceNotFoundException(String.format("user with id: %d not found",userId))) ;
        if(userSet.contains(user)){
            user.removeSong(song);
            userRepository.saveAndFlush(user);
        }
        return getById(songId);
    }

    @Transactional
    public SongResponse updateStatus(Long songId){
        Song underUpdate = get(songId);
        underUpdate.setStatus(!underUpdate.isStatus());
        songRepository.saveAndFlush(underUpdate);
        return getById(songId);
    }

    public List<SongResponse> findBySentiment(String sentiment) {
        List<Song> songs = songRepository.
                findByLabelReturnUsersAlbums(getLabelBySentiment(sentiment));

        return songs.stream().map(this::songToSongResponse).toList();
    }
    public String getLabelBySentiment(String sentiment) {
        String url = "http://127.0.0.1:8000/sentiment";
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                .queryParam("text", sentiment);

        ResponseEntity<String> underGet = restTemplate.getForEntity(builder.toUriString(),String.class);
        if(underGet.getStatusCode().is4xxClientError()) {
            throw new ResourceNotFoundException("an error occurred");
        }
//        log.info(underGet.getBody());
        return Objects.requireNonNull(underGet.getBody()).substring(1, underGet.getBody().length()-1);
    }

    @Transactional
    public void increaseView(Long songId) {
        songRepository.updateViewCount(songId);
    }

    /////////////////////////////////////// S3 SERVICE ////////////////////////////////////////////////////////////////

//    public byte[] getSongImage(Long songId) {
//        Song underGet = get(songId);
//        if (underGet.getImage().isEmpty()) {
//            throw new ResourceNotFoundException(
//                    "song with id image :[%d] not found".formatted(songId));
//        }
//
//        byte[] songImage = s3Service.getObject(
//                "song/image/%d/%s".formatted(songId, underGet.getImage())
//        );
//        return songImage;
//    }
//
//    public byte[] getSongAudio(Long songId) {
//        Song underGet = get(songId);
//        if (underGet.getImage().isEmpty()) {
//            throw new ResourceNotFoundException(
//                    "song with id audio :[%d] not found".formatted(songId));
//        }
//
//        byte[] songAudio = s3Service.getObject(
//                "song/audio/%d/%s".formatted(songId, underGet.getAudio())
//        );
//        return songAudio;
//    }
//public void saveSongAudio(MultipartFile audio, Long songId) {
//    Song song = get(songId);
//
//    if(audio != null) {
//        song.setAudio(audio.getOriginalFilename());
//        try {
//            if(!song.getAudio().isEmpty()) {
//                s3Service.removeObject(String.format("song/audio/%d/%s",song.getId(),audio.getOriginalFilename()));
//            }
//            s3Service.putObject(String.format("song/audio/%d/%s",song.getId(),audio.getOriginalFilename()),audio.getBytes());
//            songRepository.save(song);
//        } catch (IOException e) {
//            throw new RuntimeException(e.getMessage());
//        }
//    }
//
//}
//    public void saveSongImage(MultipartFile image, Long songId) {
//        Song song = get(songId);
//        if(image != null) {
//            song.setImage(image.getOriginalFilename());
//            try {
//                if(!song.getImage().isEmpty()) {
//                    s3Service.removeObject(String.format("song/image/%d/%s",song.getId(),image.getOriginalFilename()));
//                }
//                s3Service.putObject(String.format("song/image/%d/%s",song.getId(),image.getOriginalFilename()),image.getBytes());
//                songRepository.save(song);
//            } catch (IOException e) {
//                throw new RuntimeException(e.getMessage());
//            }
//        }
//
//    }
}

package com.spotify.app.controller;

import com.spotify.app.dto.SongDTO;
import com.spotify.app.dto.request.SongRequest;
import com.spotify.app.dto.response.SongResponse;
import com.spotify.app.model.Song;
import com.spotify.app.service.SongService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/song")
@AllArgsConstructor
public class SongController {

    private final SongService songService ;


    @GetMapping("/{songId}")
    public SongResponse getById(
            @PathVariable("songId") Long songId
    ) {
        return songService.getById(songId);
    }

    @PostMapping("/upload/audio/{songId}")
    public ResponseEntity<?> uploadPhoto(
            @RequestParam("audio") MultipartFile audio,
            @PathVariable("songId") Long songId
    ) throws IOException {

        // Todo: save audio
        songService.saveSongAudio(audio, songId);

        return ResponseEntity.ok().body("OK");
    }

    @PostMapping("/upload/image/{songId}")
    public ResponseEntity<?> uploadImage(
            @RequestParam("image") MultipartFile image,
            @PathVariable("songId") Long songId
    )  {

        songService.saveSongImage(image, songId);

        return ResponseEntity.ok().body("Save image of song success");
    }

    @GetMapping(
            value = "/viewImage/{songId}",
            produces = {MediaType.IMAGE_PNG_VALUE,MediaType.IMAGE_JPEG_VALUE}
    )
    public ResponseEntity<?> viewImage(@PathVariable("songId") Long songId) {
        Song song = songService.get(songId);
        return ResponseEntity.ok()
                .body(song.getImage());
    }

    @GetMapping("/findBy/playlist/{playlistId}")
    public List<SongDTO> findByPlaylistId(@PathVariable("playlistId") Long playlistId) {
        return songService.findByPlaylistId(playlistId);
    }



    @GetMapping
    public List<SongDTO> findAll() {
        return songService.findAll();
    }

    @GetMapping("/admin/{songId}")
    public Song getByIdForAdmin(
            @PathVariable("songId") Long songId
    ) {
        return songService.get(songId);
    }

    @PostMapping("/admin/save")
    public ResponseEntity<?> addPlaylist(@RequestParam(value = "image",required = false) MultipartFile image,
                                         @RequestParam("audio") MultipartFile audio,
                                         @RequestParam("lyric") String lyric,
                                         @RequestParam("genre") String genre,
                                         @RequestParam("name") String name,
                                         @RequestParam("duration") int duration,
                                         @RequestParam("userId") Long userId
    ) throws IOException {
        songService.addSong(image,audio, lyric,genre,name,duration,userId);
        return ResponseEntity.ok().body("Save playlist success");
    }

    @PostMapping("/test/admin/save")
    public ResponseEntity<?> savePlaylist(@Valid SongRequest request)  {
        songService.saveSong(request);
        return ResponseEntity.ok().body("Save playlist success");
    }


//    @PostMapping("/admin/save")
//    @Operation(description = "Save file image end with `png` only")
//    public ResponseEntity<?> addPlaylistV2(
//            @Valid @RequestBody SongDTO request
//    ) throws IOException {
//        return ResponseEntity.ok().body("Save playlist success");
//    }

    @GetMapping("/search/{name}")

    List<SongResponse> findByNameFullText(
            @PathVariable("name") String name
    ) {
        return songService.findByNameFullText(name);
    }


//    @GetMapping("/audio")
//    public ResponseEntity<?> testing() {
//        Song song = songService.get(1l);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//        headers.setContentDispositionFormData("attachment", "music.mp3");
//
//        return ResponseEntity.ok().headers(headers).body(song.getAudioTest());
//    }

}

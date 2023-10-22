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
    public ResponseEntity<?> uploadAudio(
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


    @GetMapping(value = "/audio/{songId}",produces = "audio/mpeg")
    public ResponseEntity<?> streamAudio(
            @PathVariable("songId") Long songId
    ) {

        return ResponseEntity.ok()
                .body(songService.getSongAudio(songId));
    }

    @GetMapping(
            value = "/view/image/{songId}",
            produces = {MediaType.IMAGE_PNG_VALUE,MediaType.IMAGE_JPEG_VALUE}
    )
    public ResponseEntity<?> viewImage(@PathVariable("songId") Long songId) {

        return ResponseEntity.ok()
                .body(songService.getSongImage(songId));
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



    @PostMapping("/test/admin/save")
    public ResponseEntity<?> savePlaylist(@Valid SongRequest request)  {
        songService.saveSong(request);
        return ResponseEntity.ok().body("Save playlist success");
    }



    @PostMapping
    public ResponseEntity<?> saveSong(
            @Valid SongRequest request
    )  {
        return ResponseEntity.ok().body(songService.saveSong(request));
    }

    @PutMapping("/{songId}")
    public ResponseEntity<?> updateSong(
            @Valid SongRequest request,
            @PathVariable("songId") Long songId
    )  {
        return ResponseEntity.ok().body(songService.updateSong(request, songId));
    }

    @PutMapping("/status/{songId}")
    public ResponseEntity<?> updateStatus(
            @PathVariable("songId") Long songId
    )  {
        songService.updateStatus(songId);
        return ResponseEntity.ok().body("update status of song success");
    }




    @GetMapping("/search/{name}")
    List<SongResponse> findByNameFullText(
            @PathVariable("name") String name
    ) {
        return songService.findByNameFullText(name);
    }


    @GetMapping("/find/by/sentiment/{sentiment}")
    public List<SongResponse> findBySentiment(@PathVariable("sentiment")String sentiment) {
        return songService.findBySentiment(sentiment);
    }


}

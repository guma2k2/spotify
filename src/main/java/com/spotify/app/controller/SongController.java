package com.spotify.app.controller;

import com.spotify.app.dto.SongResponseDTO;
import com.spotify.app.model.Song;
import com.spotify.app.model.User;
import com.spotify.app.service.SongService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/song")
@AllArgsConstructor
public class SongController {

    private final SongService songService ;


    @GetMapping("/{songId}")
    public SongResponseDTO getById(
            @PathVariable("songId") Long songId
    ) {
        return songService.getById(songId);
    }

    @PostMapping("/uploadAudio/{songId}")
    public ResponseEntity<?> uploadPhoto(
            @RequestParam("audio") MultipartFile audio,
            @PathVariable("songId") Long songId
    ) throws IOException {

        // Todo: save audio

        songService.saveSongAudio(audio, songId);

        return ResponseEntity.ok().body("OK");
    }

    @PostMapping("/uploadImage/{songId}")
    @Operation(description = "Save file image end with `png` only")
    public ResponseEntity<?> uploadImage(
            @RequestParam("image") MultipartFile image,
            @PathVariable("songId") Long songId
    )  {

        // Todo: save image

        songService.saveSongImage(image, songId);

        return ResponseEntity.ok().body("Save image of song success");
    }

    @GetMapping("/viewImage/{songId}")
    public ResponseEntity<?> readImage(@PathVariable("songId") Long songId) {
        Song song = songService.get(songId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("image/png"))
                .body(song.getImage());
    }

}

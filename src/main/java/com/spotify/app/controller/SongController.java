package com.spotify.app.controller;

import com.spotify.app.dto.SongResponseDTO;
import com.spotify.app.service.SongService;
import lombok.AllArgsConstructor;
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

}

package com.spotify.app.controller;

import com.spotify.app.dto.SongDTO;
import com.spotify.app.dto.request.SongRequest;
import com.spotify.app.dto.response.SongResponse;
import com.spotify.app.dto.response.SongSearchResponse;
import com.spotify.app.service.SongService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
        return ResponseEntity.ok().body(songService.saveSongAudio(audio, songId));
    }

    @PostMapping("/upload/image/{songId}")
    public ResponseEntity<?> uploadImage(
            @RequestParam("image") MultipartFile image,
            @PathVariable("songId") Long songId
    )  {
        return ResponseEntity.ok().body(songService.saveSongImage(image, songId));
    }

    @GetMapping("/findBy/playlist/{playlistId}")
    public List<SongDTO> findByPlaylistId(@PathVariable("playlistId") Long playlistId) {
        return songService.findByPlaylistId(playlistId);
    }

    @GetMapping
    public List<SongDTO> findAll() {
        return songService.findAll();
    }


    @PostMapping("/save")
    @Operation(description = "Save song, format of genre: {POP, CLASSICAL, Rock, Hip_Hop}")
    public ResponseEntity<?> saveSong(
            @Valid @RequestBody SongRequest request
    )  {
        return ResponseEntity.ok().body(songService.saveSong(request));
    }

    @PutMapping("/update/{songId}")
    @Operation(description = "Update song by id, format of genre: {POP, CLASSICAL, Rock, Hip_Hop}")
    public ResponseEntity<?> updateSong(
            @Valid @RequestBody SongRequest request,
            @PathVariable("songId") Long songId
    )  {
        return ResponseEntity.ok().body(songService.updateSong(request, songId));
    }

    @PutMapping("/status/{songId}")
//    @PreAuthorize("hasAnyRole('ARTIST', 'ADMIN')")
    public ResponseEntity<?> updateStatus(
            @PathVariable("songId") Long songId
    )  {
        return ResponseEntity.ok().body(songService.updateStatus(songId));
    }

    @GetMapping("/{songId}/add/{userId}")
//    @PreAuthorize("hasRole('ARTIST')")
    public ResponseEntity<?> addUser(
            @PathVariable("songId")Long songId,
            @PathVariable("userId")Long userId
    ) {
        return ResponseEntity.ok().body(songService.addUser(songId,userId));
    }

    @GetMapping("/{songId}/remove/{userId}")
//    @PreAuthorize("hasRole('ARTIST')")
    public ResponseEntity<?> removeUser(
            @PathVariable("songId")Long songId,
            @PathVariable("userId")Long userId
    ) {
        return ResponseEntity.ok().body(songService.removeUser(songId,userId));
    }

    @GetMapping("/search/{name}")
    List<SongSearchResponse> findByNameFullText(
            @PathVariable("name") String name
    ) {
        return songService.findByNameFullText(name);
    }


    @GetMapping("/find/by/sentiment/{sentiment}")
    public List<SongResponse> findBySentiment(@PathVariable("sentiment")String sentiment) {
        return songService.findBySentiment(sentiment);
    }

    @GetMapping("/increase/view/{songId}")
    @Operation(description = "Increase viewCount's song.")
    public ResponseEntity<?> increaseViewCountSong(
            @PathVariable("songId") Long songId
    ) {
        songService.increaseView(songId);
        return ResponseEntity.ok().body(String.format("increased view of song %d",songId));
    }


    //////////////////////////////////// S3 SERVICE ////////////////////////////////////////
//    @GetMapping(value = "/audio/{songId}",produces = "audio/mpeg")
//    public ResponseEntity<?> streamAudio(
//            @PathVariable("songId") Long songId
//    ) {
//
//        return ResponseEntity.ok()
//                .body(songService.getSongAudio(songId));
//    }
//
//    @GetMapping(
//            value = "/view/image/{songId}",
//            produces = {MediaType.IMAGE_PNG_VALUE,MediaType.IMAGE_JPEG_VALUE}
//    )
//    public ResponseEntity<?> viewImage(@PathVariable("songId") Long songId) {
//
//        return ResponseEntity.ok()
//                .body(songService.getSongImage(songId));
//    }


}

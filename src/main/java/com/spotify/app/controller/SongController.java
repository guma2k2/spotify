package com.spotify.app.controller;

import com.spotify.app.dto.SongDTO;
import com.spotify.app.dto.response.SongResponseDTO;
import com.spotify.app.model.Song;
import com.spotify.app.service.SongService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(description = "Save file image end with `png` only")
    public ResponseEntity<?> addPlaylist(@RequestParam("image") MultipartFile image,
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

    @GetMapping("/search/{name}")
    List<SongResponseDTO> findByNameFullText(
            @PathVariable("name") String name
    ) {
        return songService.findByNameFullText(name);
    }


}

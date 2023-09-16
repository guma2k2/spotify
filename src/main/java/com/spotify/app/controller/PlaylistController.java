package com.spotify.app.controller;

import com.spotify.app.dto.PlaylistDTO;
import com.spotify.app.dto.PlaylistResponseDTO;
import com.spotify.app.model.Category;
import com.spotify.app.model.Playlist;
import com.spotify.app.repository.PlaylistRepository;
import com.spotify.app.service.PlaylistService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/playlist")
@AllArgsConstructor
public class PlaylistController {
    private final PlaylistService playlistService ;


    @GetMapping("/findByUser/{userId}")
    public List<PlaylistResponseDTO> findByUserId(@PathVariable("userId") Long userId) {
        return playlistService.findByUserId(userId) ;
    }

    @GetMapping("/{id}")
    public PlaylistDTO findById(@PathVariable("id") Long id) {
        return playlistService.findByIdWithSongs(id) ;
    }

    @PostMapping("/uploadFile/{playlistId}")
    @Operation(description = "Save file image end with `png` only")
    public ResponseEntity<?> uploadFiles(@RequestParam("image") MultipartFile image,
                                         @RequestParam("thumbnail") MultipartFile thumbnail,
                                         @PathVariable("playlistId") Long playlistId){
        playlistService.uploadFiles(image,thumbnail,playlistId);
        return ResponseEntity.ok().body(String.format("Upload files for playlist %d success",playlistId));
    }

    @GetMapping("/viewImage/{playlistId}")
    public ResponseEntity<?> viewImage(@PathVariable("playlistId") Long playlistId) {
        Playlist playlist = playlistService.get(playlistId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("image/png"))
                .body(playlist.getImage());
    }
    @GetMapping("/viewThumbnail/{playlistId}")
    public ResponseEntity<?> viewThumbnail(@PathVariable("playlistId") Long playlistId) {
        Playlist playlist = playlistService.get(playlistId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("image/png"))
                .body(playlist.getThumbnail());
    }
}

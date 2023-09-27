package com.spotify.app.controller;

import com.spotify.app.dto.PlaylistDTO;
import com.spotify.app.dto.response.PlaylistResponseDTO;
import com.spotify.app.model.Playlist;
import com.spotify.app.service.PlaylistService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
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

    @PutMapping("/admin/update/{playlistId}")
    @Operation(description = "Save file image end with `png` only")
    public ResponseEntity<?> uploadPlaylist(@RequestParam("image") MultipartFile image,
                                            @RequestParam("thumbnail") MultipartFile thumbnail,
                                            @PathVariable("playlistId") Long playlistId,
                                            @RequestParam("description") String description,
                                            @RequestParam("name") String name
    ){
        playlistService.updatePlaylist(image,thumbnail,playlistId,description,name);
        return ResponseEntity.ok().body(String.format("Update playlist %d success", playlistId));
    }

    @PostMapping("/admin/save")
    @Operation(description = "Save file image end with `png` only")
    public ResponseEntity<?> addPlaylist(@RequestParam(value = "image",required = false) MultipartFile image,
                                         @RequestParam(value = "thumbnail",required = false) MultipartFile thumbnail,
                                         @RequestParam("description") String description,
                                         @RequestParam("name") String name
    ){
        playlistService.addPlaylist(image,thumbnail,description,name);
        return ResponseEntity.ok().body("Save playlist success");
    }

    @GetMapping("/{playlistId}/add/{userId}")
    public ResponseEntity<?> addUserToLikedPlaylist(
            @PathVariable("playlistId") Long playlistId,
            @PathVariable("userId") Long userId
    ) {
        playlistService.addUserToLikedPlaylist(userId,playlistId);
        return ResponseEntity.ok().body(String.format("Add user %d to playlist %d successful",userId,playlistId));
    }

    @GetMapping("/{playlistId}/remove/{userId}")
    public ResponseEntity<?> removeUserFromLikedPlaylist(
            @PathVariable("playlistId") Long playlistId,
            @PathVariable("userId") Long userId
    ) {
        playlistService.removeUserFromLikedPlaylist(userId,playlistId);
        return ResponseEntity.ok().body(String.format("Remove user %d from playlist %d successful",userId,playlistId));
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

    @GetMapping
    public List<PlaylistResponseDTO> listAll() {
        return playlistService.listAll();
    }

    @GetMapping("/admin/{playlistId}")
    public PlaylistResponseDTO getPlaylistForAdmin(@PathVariable("playlistId") Long playlistId) {
        return playlistService.getPlaylistForAdmin(playlistId);
    }
    @GetMapping("/admin/{playlistId}/add/{songId}")
    public ResponseEntity<String> addPlaylist(
            @PathVariable("playlistId") Long playlistId,
            @PathVariable("songId") Long songId
    ) {
        playlistService.addSong(playlistId,songId);
        return ResponseEntity.
                ok().
                body(String.
                        format("playlist with id: %d add song with id: %d success", playlistId, songId));
    }

    @GetMapping("/admin/{playlistId}/remove/{songId}")
    public ResponseEntity<String> removePlaylist(
            @PathVariable("playlistId") Long playlistId,
            @PathVariable("songId") Long songId
    ) {
        playlistService.removeSong(playlistId,songId);
        return ResponseEntity.
                ok().
                body(String.
                        format("playlist with id: %d remove song with id: %d success", playlistId, songId));
    }
}

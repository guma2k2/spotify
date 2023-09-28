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


    @GetMapping("/user/{userId}/add/{songId}")
    @Operation(description = "add song to liked Song by userId")
    public void addLikedSongToPlaylistByUserId(
            @PathVariable("userId") Long userId,
            @PathVariable("songId") Long songId
    ) {
        playlistService.addSongToLikedPlaylist(userId, songId);
    }

    @GetMapping("/user/{userId}/remove/{songId}")
    @Operation(description = "remove song from liked Song by userId")
    public void removeFromLikedSongByUserId(
            @PathVariable("userId") Long userId,
            @PathVariable("songId") Long songId
    ) {
        playlistService.removeSongFromLikedPlaylist(userId,songId);
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
    public ResponseEntity<String> addPlaylist(@RequestParam(value = "image",required = false) MultipartFile image,
                                         @RequestParam(value = "thumbnail",required = false) MultipartFile thumbnail,
                                         @RequestParam("description") String description,
                                         @RequestParam("name") String name
    ){
        playlistService.addPlaylist(image,thumbnail,description,name);
        return ResponseEntity.ok().body("Save playlist success");
    }




    @GetMapping(
            value = "/viewImage/{playlistId}",
            produces = {MediaType.IMAGE_PNG_VALUE,MediaType.IMAGE_JPEG_VALUE}
    )
    public ResponseEntity<?> viewImage(@PathVariable("playlistId") Long playlistId) {
        Playlist playlist = playlistService.get(playlistId);
        return ResponseEntity.ok()
                .body(playlist.getImage());
    }
    @GetMapping(
            value = "/viewThumbnail/{playlistId}",
            produces = {MediaType.IMAGE_PNG_VALUE,MediaType.IMAGE_JPEG_VALUE}
    )
    public ResponseEntity<?> viewThumbnail(@PathVariable("playlistId") Long playlistId) {
        Playlist playlist = playlistService.get(playlistId);
        return ResponseEntity.ok()
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
                body(String.format("playlist with id: %d add song with id: %d success", playlistId, songId));
    }

    @GetMapping("/admin/{playlistId}/remove/{songId}")
    @Operation(description = "call this api to remove playlist " +
            "when undo like playlist or remove playlist")
    public ResponseEntity<String> removePlaylist(
            @PathVariable("playlistId") Long playlistId,
            @PathVariable("songId") Long songId
    ) {
        playlistService.removeSong(playlistId,songId);
        return ResponseEntity.
                ok().
                body(String.format("playlist with id: %d remove song with id: %d success", playlistId, songId));
    }


    @GetMapping("/{userId}/create/playlist")
    public ResponseEntity<?> createPlaylistByUserId(@PathVariable("userId") Long userId) {
        playlistService.createPlaylistByUserId(userId);
        return ResponseEntity.ok().body("playlist is created");
    }
}

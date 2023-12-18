package com.spotify.app.controller;

import com.spotify.app.dto.PlaylistDTO;
import com.spotify.app.dto.response.PlaylistResponse;
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
    public List<PlaylistResponse> findByUserId(@PathVariable("userId") Long userId) {
        return playlistService.findByUserId(userId) ;
    }

    @GetMapping("/{id}")
    public PlaylistDTO findById(@PathVariable("id") Long id) {
        return playlistService.findByIdReturnSongs(id) ;
    }

    @GetMapping("/user/{userId}/add/{songId}")
    @Operation(description = "add song to liked Song by userId")
    public ResponseEntity<?> addLikedSongToPlaylistByUserId(
            @PathVariable("userId") Long userId,
            @PathVariable("songId") Long songId
    ) {
        Long savedPlaylistId = playlistService.addSongToLikedPlaylist(userId, songId);
        return ResponseEntity.ok().body(playlistService.findByIdReturnSongs(savedPlaylistId));
    }

    @GetMapping("/user/{userId}/remove/{songId}")
    @Operation(description = "remove song from liked Song by userId")
    public ResponseEntity<?> removeFromLikedSongByUserId(
            @PathVariable("userId") Long userId,
            @PathVariable("songId") Long songId
    ) {
        Long removedPlaylistId = playlistService.removeSongFromLikedPlaylist(userId, songId);
        return ResponseEntity.ok().body(playlistService.findByIdReturnSongs(removedPlaylistId));
    }

    @PutMapping("/admin/update/{playlistId}")
    @Operation(description = "update playlist")
    public ResponseEntity<?> uploadPlaylist(
                                @PathVariable("playlistId") Long playlistId,
                                @RequestParam(value = "description", required = false) String description,
                                @RequestParam("name") String name
    ){
        playlistService.updatePlaylist(playlistId,description,name);
        return ResponseEntity.ok().body(String.format("Update playlist %d success", playlistId));
    }

    @PostMapping("/admin/save")
    @Operation(description = "Save playlist")
    public ResponseEntity<String> addPlaylist(
                                @RequestParam(value = "description", required = false) String description,
                                @RequestParam("name") String name
    ){
        playlistService.addPlaylist(description,name);
        return ResponseEntity.ok().body("Save playlist success");
    }


    @PostMapping("/upload/image/{playlistId}")
    public ResponseEntity<?> uploadImage(
            @RequestParam("image") MultipartFile image,
            @PathVariable("playlistId") Long playlistId
    )  {

        playlistService.savePlaylistImage(image, playlistId);
        return ResponseEntity.ok().body("Save image of playlist success");
    }

    @PostMapping("/upload/thumbnail/{playlistId}")
    public ResponseEntity<?> uploadThumbnail(
            @RequestParam("thumbnail") MultipartFile thumbnail,
            @PathVariable("playlistId") Long playlistId
    )  {

        playlistService.savePlaylistThumbnail(thumbnail, playlistId);
        return ResponseEntity.ok().body("Save thumbnail of playlist success");
    }

    @GetMapping
    public List<PlaylistResponse> listAll() {
        return playlistService.listAll();
    }

    @GetMapping("/admin/{playlistId}")
    public PlaylistResponse getPlaylistForAdmin(@PathVariable("playlistId") Long playlistId) {
        return playlistService.getPlaylistByIdForAdmin(playlistId);
    }

    @GetMapping("/{playlistId}/add/song/{songId}")
    @Operation(description = "add song to target playlist")
    public ResponseEntity<?> addSongByPlaylist(
            @PathVariable("playlistId") Long playlistId,
            @PathVariable("songId") Long songId
    ) {
        playlistService.addSong(playlistId,songId);
        return ResponseEntity.ok().body(playlistService.findByIdReturnSongs(playlistId));
    }

    @GetMapping("/{playlistId}/remove/song/{songId}")
    @Operation(description = "call this api to remove playlist " +
            "when undo like playlist or remove playlist")
    public ResponseEntity<?> removeSongFromPlaylist(
            @PathVariable("playlistId") Long playlistId,
            @PathVariable("songId") Long songId
    ) {
        playlistService.removeSong(playlistId,songId);
        return ResponseEntity.ok().body(playlistService.findByIdReturnSongs(playlistId));
    }

    @GetMapping("/{userId}/create/playlist")
    @Operation(description = "call this api to add playlist " +
            "when undo like playlist or remove playlist")
    public ResponseEntity<?> createPlaylistByUserId(@PathVariable("userId") Long userId) {
        playlistService.createPlaylistByUserId(userId);
        return ResponseEntity.ok().body("playlist is created");
    }

}

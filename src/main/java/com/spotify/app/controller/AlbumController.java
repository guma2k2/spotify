package com.spotify.app.controller;


import com.spotify.app.dto.AlbumDTO;
import com.spotify.app.dto.request.AlbumRequest;
import com.spotify.app.dto.response.AlbumResponse;
import com.spotify.app.model.Album;
import com.spotify.app.service.AlbumService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/album")
@AllArgsConstructor
public class AlbumController {
    private final AlbumService albumService ;

    @GetMapping("/{id}")
    public AlbumDTO findById(@PathVariable("id") Long id) {
        return albumService.findById(id);
    }


    @PostMapping("/upload/image/{songId}")
    public ResponseEntity<?> uploadImage(
            @RequestParam("image") MultipartFile image,
            @PathVariable("songId") Long songId
    )  {
        albumService.saveAlbumImage(image, songId);
        return ResponseEntity.ok().body("Save image of song success");
    }

    @PostMapping("/upload/thumbnail/{songId}")
    public ResponseEntity<?> uploadThumbnail(
            @RequestParam("image") MultipartFile thumbnail,
            @PathVariable("songId") Long songId
    )  {

        albumService.saveAlbumThumbnail(thumbnail, songId);
        return ResponseEntity.ok().body("Save image of song success");
    }

    @GetMapping(
            value = "/view/image/{albumId}",
            produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE}
    )
    public ResponseEntity<?> viewImage(@PathVariable("albumId") Long albumId) {
        return ResponseEntity.ok()
                .body(albumService.getAlbumImage(albumId));
    }

    @GetMapping(
            value = "/view/thumbnail/{albumId}",
            produces = {MediaType.IMAGE_PNG_VALUE,MediaType.IMAGE_JPEG_VALUE}
    )
    public ResponseEntity<?> viewThumbnail(@PathVariable("albumId") Long albumId) {
        return ResponseEntity.ok()
                .body(albumService.getAlbumThumbnail(albumId));
    }

    @GetMapping("/{albumId}/add/{songId}")
    @Operation(description = "add song to liked Song by userId")
    public void addLikedSongToPlaylistByUserId(
            @PathVariable("albumId") Long albumId,
            @PathVariable("songId") Long songId
    ) {
        albumService.addSong(albumId, songId);
    }

    @GetMapping("/{albumId}/remove/{songId}")
    @Operation(description = "remove song from liked Song by userId")
    public void removeFromLikedSongByUserId(
            @PathVariable("albumId") Long albumId,
            @PathVariable("songId") Long songId
    ) {
        albumService.removeSong(albumId,songId);
    }

    @GetMapping
    public List<AlbumResponse> findAll(){
        return albumService.findAll();
    }


    @PostMapping("/{userID}/add")
    public ResponseEntity<?> addAlbumByUserId(
            @PathVariable("userID") Long userID,
            @Valid @RequestBody AlbumRequest request
    ) {
        Long albumId = albumService.addAlbum(userID,request);
        return ResponseEntity.ok().body(albumId);
    }


    @PutMapping("/update/{albumId}")
    public ResponseEntity<?> updateAlbum(
        @PathVariable("albumId") Long albumId,
        @RequestBody AlbumRequest request
    ) {
        return ResponseEntity.ok().body(albumService.updateAlbum(albumId, request));
    }





}

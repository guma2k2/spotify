package com.spotify.app.controller;


import com.spotify.app.dto.AlbumDTO;
import com.spotify.app.dto.request.AlbumRequest;
import com.spotify.app.dto.response.AlbumResponse;
import com.spotify.app.model.Album;
import com.spotify.app.model.User;
import com.spotify.app.security.auth.AuthUserDetails;
import com.spotify.app.service.AlbumService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/album")
@AllArgsConstructor
@Slf4j
public class AlbumController {
    private final AlbumService albumService ;

    @GetMapping("/{id}")
    public AlbumDTO findById(@PathVariable("id") Long id) {
        return albumService.findById(id);
    }


    @PostMapping("/upload/image/{albumId}")
    public ResponseEntity<?> uploadImage(
            @RequestParam("image") MultipartFile image,
            @PathVariable("albumId") Long albumId
    )  {
        albumService.saveAlbumImage(image, albumId);
        return ResponseEntity.ok().body(albumService.findById(albumId));
    }

    @PostMapping("/upload/thumbnail/{albumId}")
    public ResponseEntity<?> uploadThumbnail(
            @RequestParam("thumbnail") MultipartFile thumbnail,
            @PathVariable("albumId") Long albumId
    )  {
        albumService.saveAlbumThumbnail(thumbnail, albumId);
        return ResponseEntity.ok().body(albumService.findById(albumId));
    }

    @GetMapping("/{albumId}/add/{songId}")
    public ResponseEntity<?> addSongToAlbum(
            @PathVariable("albumId") Long albumId,
            @PathVariable("songId") Long songId
    ) {
        albumService.addSong(albumId, songId);
        return ResponseEntity.ok().body(albumService.findById(albumId));
    }

    @GetMapping("/{albumId}/remove/{songId}")
    public ResponseEntity<?> removeSongFromAlbum(
            @PathVariable("albumId") Long albumId,
            @PathVariable("songId") Long songId
    ) {
        albumService.removeSong(albumId,songId);
        return ResponseEntity.ok().body(albumService.findById(albumId));
    }

    @GetMapping
    public List<AlbumResponse> findAll(){
        return albumService.findAll();
    }


    @PostMapping
    public ResponseEntity<?> saveAlbum(
            @Valid @RequestBody AlbumRequest request,
            @AuthenticationPrincipal AuthUserDetails authUserDetails
    ) {
        Long savedAlbumId = albumService.addAlbum(authUserDetails,request);
        return ResponseEntity.ok().body(albumService.findById(savedAlbumId));
    }


    @PutMapping("/update/{albumId}")
    public ResponseEntity<?> updateAlbum(
        @PathVariable("albumId") Long albumId,
        @Valid @RequestBody AlbumRequest request
    ) {
        albumService.updateAlbum(albumId, request);
        return ResponseEntity.ok().body(albumService.findById(albumId));
    }

    @PutMapping("/update/status/{albumId}")
    public ResponseEntity<?> updateStatusAlbum(
            @PathVariable("albumId") Long albumId
    ) {
        return ResponseEntity.ok().body(albumService.updateStatusAlbum(albumId));
    }

    @GetMapping("/admin/{id}")
    public AlbumResponse findByIdByAdmin(@PathVariable("id") Long id) {
        return albumService.findByIdByAdmin(id);
    }



}

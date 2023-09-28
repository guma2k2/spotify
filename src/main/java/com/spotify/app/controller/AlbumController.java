package com.spotify.app.controller;


import com.spotify.app.dto.AlbumDTO;
import com.spotify.app.model.Album;
import com.spotify.app.model.User;
import com.spotify.app.service.AlbumService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/album")
@AllArgsConstructor
public class AlbumController {
    private final AlbumService albumService ;

    @GetMapping("/{id}")
    public AlbumDTO findById(@PathVariable("id") Long id) {
        return albumService.findById(id);
    }


    @PostMapping(
            value = "/uploadFile/{albumId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<?> uploadFiles(@RequestParam("image")MultipartFile image,
                                         @RequestParam("thumbnail") MultipartFile thumbnail,
                                         @PathVariable("albumId") Long albumId){
        albumService.uploadFiles(image,thumbnail,albumId);
        return ResponseEntity.ok().body(String.format("Upload files for album %d success",albumId));
    }

    @GetMapping(
            value = "/viewImage/{albumId}",
            produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE}
    )
    public ResponseEntity<?> viewImage(@PathVariable("albumId") Long albumId) {
        Album album = albumService.get(albumId);
        return ResponseEntity.ok()
                .body(album.getImage());
    }
    @GetMapping(
            value = "/viewThumbnail/{albumId}",
            produces = {MediaType.IMAGE_PNG_VALUE,MediaType.IMAGE_JPEG_VALUE}
    )
    public ResponseEntity<?> viewThumbnail(@PathVariable("albumId") Long albumId) {
        Album album = albumService.get(albumId);
        return ResponseEntity.ok()
                .body(album.getThumbnail());
    }

}

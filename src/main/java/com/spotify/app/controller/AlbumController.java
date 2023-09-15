package com.spotify.app.controller;


import com.spotify.app.dto.AlbumDTO;
import com.spotify.app.service.AlbumService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/album")
@AllArgsConstructor
public class AlbumController {
    private final AlbumService albumService ;

    @GetMapping("/{id}")
    public AlbumDTO findById(@PathVariable("id") Long id) {
        return albumService.findById(id);
    }
}

package com.spotify.app.controller;

import com.spotify.app.dto.PlaylistDTO;
import com.spotify.app.dto.PlaylistResponseDTO;
import com.spotify.app.repository.PlaylistRepository;
import com.spotify.app.service.PlaylistService;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

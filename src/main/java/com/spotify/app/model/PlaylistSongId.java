package com.spotify.app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
public class PlaylistSongId implements Serializable {
    @Column(name = "playlist_id")
    private Long playlistId ;
    @Column(name = "song_id")
    private Long songId;


    public PlaylistSongId(Long playlistId, Long songId) {
        this.playlistId = playlistId;
        this.songId = songId ;
    }
}

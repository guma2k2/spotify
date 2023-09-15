package com.spotify.app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
public class AlbumSongId implements Serializable {
    @Column(name = "album_id")
    private Long albumId ;
    @Column(name = "song_id")
    private Long songId;

    public AlbumSongId(Long albumId, Long songId) {
        this.albumId = albumId ;
        this.songId = songId ;
    }
}

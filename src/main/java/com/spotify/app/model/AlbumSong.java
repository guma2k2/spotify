package com.spotify.app.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Iterator;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "album_song")
@Builder
public class AlbumSong {

    @EmbeddedId
    private AlbumSongId id;

    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.MERGE})
    @MapsId("album_id")
    private Album album ;

    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.MERGE})
    @MapsId("song_id")
    private Song song ;


    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createdOn ;

    public AlbumSong(Album album, Song song) {
        this.album = album;
        this.song = song;
        this.id = new AlbumSongId(album.getId(), song.getId());
        this.createdOn = LocalDateTime.now();
    }
}

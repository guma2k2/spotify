package com.spotify.app.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "playlist_song")
@Builder
public class PlaylistSong {

    @EmbeddedId
    private PlaylistSongId id;

    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.MERGE})
    @MapsId("playlist_id")
    private Playlist playlist ;


    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.MERGE})
    @MapsId("song_id")
    private Song song ;


    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createdOn ;

    public PlaylistSong(Playlist playlist, Song song) {
        this.playlist = playlist;
        this.song = song ;
        this.id = new PlaylistSongId(playlist.getId(), song.getId());
        this.createdOn = LocalDateTime.now();
    }
}

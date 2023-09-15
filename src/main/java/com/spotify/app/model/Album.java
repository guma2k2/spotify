package com.spotify.app.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "album")
@Builder
@Data
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String name ;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime releaseDate ;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image ;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] thumbnail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user ;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "album")
    @Builder.Default
    private List<AlbumSong> albumSongList = new ArrayList<>();


    public void addSong(Song song) {
        AlbumSong albumSong = new AlbumSong(this, song);
        albumSongList.add(albumSong) ;
    }

    public void removeSong(Song song) {
        for (Iterator<AlbumSong> iterator = albumSongList.iterator();
             iterator.hasNext(); ) {
            AlbumSong albumSong = iterator.next();

            if (albumSong.getAlbum().equals(this) &&
                    albumSong.getSong().equals(song)) {
                iterator.remove();
                albumSong.setAlbum(null);
                albumSong.setSong(null);
            }
        }
    }
}

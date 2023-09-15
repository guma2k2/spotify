package com.spotify.app.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "playlist")
@Builder
@Data
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Column(length = 30)
    private String name ;

    @Column(length = 50)
    private String description;
    private String image ;

    private String thumbnail ;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "playlist")
    @Builder.Default
    private List<PlaylistSong> playlistSongList = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user ;

    @ManyToMany(mappedBy = "playlists")
    @JsonBackReference
    @Builder.Default
    private Set<Category> categories = new HashSet<>() ;

    @Transient
    public int getSumViewCount() {
        return playlistSongList.stream()
                .mapToInt((playlistSong) -> playlistSong.getSong()
                        .getDuration())
                .sum();
    }

    @Transient
    public int getSumSongCount() {
        return playlistSongList.stream()
                .mapToInt((playlistSong) -> playlistSong.getSong() != null ? 1 : 0).sum();
    }

    public void addSong(Song song) {
        PlaylistSong playlistSong = new PlaylistSong(this, song);
        playlistSongList.add(playlistSong) ;
    }

    public void removeSong(Song song) {
        for (Iterator<PlaylistSong> iterator = playlistSongList.iterator();
             iterator.hasNext(); ) {
            PlaylistSong playlistSong = iterator.next();

            if (playlistSong.getPlaylist().equals(this) &&
                    playlistSong.getSong().equals(song)) {
                iterator.remove();
                playlistSong.setPlaylist(null);
                playlistSong.setSong(null);
            }
        }
    }


}

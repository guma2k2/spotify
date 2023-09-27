package com.spotify.app.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.spotify.app.utility.FileUploadUtil;
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

    @Column(length = 100)
    private String name ;

    @Column(length = 300)
    private String description;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] thumbnail;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "playlist")
    @Builder.Default
    private List<PlaylistSong> playlistSongList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "playlist")
    @Builder.Default
    private List<PlaylistUser> playlistUserList = new ArrayList<>();

    @ManyToMany(mappedBy = "playlists")
    @JsonBackReference
    @Builder.Default
    private Set<Category> categories = new HashSet<>() ;


    @Transient
    public long getLikedCount() {
        return this.playlistUserList.size();
    }

    @Transient
    public long getSumViewCount() {
        return playlistSongList.stream()
                .mapToLong((playlistSong) -> playlistSong.getSong()
                        .getViewCount())
                .sum();
    }

    @Transient
    public int getSumSongCount() {
        return playlistSongList.stream()
                .mapToInt((playlistSong) -> playlistSong.getSong() != null ? 1 : 0).sum();
    }

    @Transient
    public String getImagePath() {
        String baseUrl = FileUploadUtil.baseUrl;
        if(image!=null) {
            return baseUrl+"/playlist/viewImage/" + this.id ;
        }
        return FileUploadUtil.baseUrlFail;
    }
    @Transient
    public String getThumbnailPath() {
        String baseUrl = FileUploadUtil.baseUrl;
        if(thumbnail!=null) {
            return baseUrl+"/playlist/viewThumbnail/" + this.id ;
        }
        return FileUploadUtil.baseUrlFail;
    }


    public void addUser(User user) {
        PlaylistUser playlistUser = new PlaylistUser(this,user);
        playlistUserList.add(playlistUser);
    }


    public void addSong(Song song) {
        PlaylistSong playlistSong = new PlaylistSong(this, song);
        playlistSongList.add(playlistSong) ;
    }

    public void removeSong(Song song) {
        for (Iterator<PlaylistSong> iterator = this.playlistSongList.iterator(); iterator.hasNext(); ) {
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

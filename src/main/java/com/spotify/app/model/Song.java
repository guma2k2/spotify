package com.spotify.app.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.spotify.app.enums.Genre;
import com.spotify.app.utility.FileUploadUtil;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "song")
@Builder
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Column(length = 50,unique = true)
    private String name ;


    @Column(length = 5000)
    private String lyric;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime releaseDate ;

    private long viewCount ;

    @Enumerated(EnumType.STRING)
    private Genre genre ;

    private int duration;

    private String audio;

    private String image;

    @Builder.Default
    private boolean status = true;

    @Column(length = 30)
    private String label;

    @ManyToMany(mappedBy = "songs")
    @Builder.Default
    @JsonBackReference
    private Set<User> users = new HashSet<>() ;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "song")
    @Builder.Default
    private List<AlbumSong> albumSongList = new ArrayList<>();

    @Transient
    public String getAudioPath() {
        String baseUrl = FileUploadUtil.baseUrl;
        if(audio!=null) {
            return baseUrl+ "/song-audio/" + this.id + "/" + audio ;
        }
        return FileUploadUtil.baseUrlFail;
    }

    @Transient
    public String getImagePath() {
        String baseUrl = FileUploadUtil.baseUrl;
        if(image!=null) {
            return baseUrl+ "/song-images/" + this.id + "/" + image ;
        }
        return FileUploadUtil.baseUrlFail;
    }


    public Song(Long id) {
        this.id = id;
    }
}

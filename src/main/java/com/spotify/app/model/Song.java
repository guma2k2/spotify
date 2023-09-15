package com.spotify.app.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.spotify.app.enums.Genre;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
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

    @Column(length = 1000)
    private String lyric;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime releaseDate ;
    private long viewCount ;

    @Enumerated(EnumType.STRING)
    private Genre genre ;
    private int duration;
    private String audio;
    private String image ;

    @ManyToMany(mappedBy = "songs")
    @Builder.Default
    @JsonBackReference
    private Set<User> users = new HashSet<>() ;

    @Transient
    public String getAudioPath() {
        if (id == null || audio == null) return "https://www.pngitem.com/pimgs/m/150-1503945_transparent-user-png-default-user-image-png-png.png";
        return "/song-audios/" + this.id + "/" + this.audio;
    }


    @Transient
    public String getImagePath() {
        if (id == null || image == null) return "https://www.pngitem.com/pimgs/m/150-1503945_transparent-user-png-default-user-image-png-png.png";
        return "/song-images/" + this.id + "/" + this.image;
    }

    public Song(Long id) {
        this.id = id;
    }
}

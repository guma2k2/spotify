package com.spotify.app.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.spotify.app.enums.Gender;
import com.spotify.app.utility.FileUploadUtil;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "`user`")
@Builder
public class User  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Column(length = 20, nullable = false)
    private String firstName ;

    @Column(length = 20, nullable = false)
    private String lastName ;

    @Column(unique = true)
    private String email ;

    private String password ;

    private LocalDateTime dateOfBrith;

    private String photo;
    @Builder.Default
    private boolean status = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    @JsonBackReference
    private Role role ;

    @Enumerated(EnumType.STRING)
    private Gender gender ;

    private LocalDateTime createdOn ;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    @Builder.Default
    @JsonManagedReference
    List<Album> albums = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "user_song",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "song_id")
    )
    @Builder.Default
    @JsonManagedReference
    private Set<Song> songs = new HashSet<>();


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    @Builder.Default
    private List<PlaylistUser> playlistUserList = new ArrayList<>();


    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true, mappedBy = "followedUser")
    @Builder.Default
    private Set<Follower> followingList = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true, mappedBy = "followingUser")
    @Builder.Default
    private Set<Follower> followedList = new HashSet<>();


    public void addPlaylist(Playlist playlist) {
        PlaylistUser playlistUser = new PlaylistUser(playlist,this);
        playlistUserList.add(playlistUser);
    }

    public void removePlaylist(Playlist playlist) {
        PlaylistUser playlistUser = new PlaylistUser(playlist,this);
        playlistUserList.remove(playlistUser);
    }

    @Transient
    public String getPhotoImagePath() {
        if(photo!=null) {
            return photo;
        }
        return FileUploadUtil.baseUrlUserDefault;
    }


    public void addSong(Song song) {
        songs.add(song);
        song.getUsers().add(this);
    }

    public void removeSong(Song song) {
        songs.remove(song);
        song.getUsers().remove(this);
    }

    public void addAlbum(Album album) {
        albums.add(album) ;
        album.setUser(this);
    }

    public void removeAlbum(Album album) {
        albums.remove(album);
        album.setUser(null);
    }

    @Transient
    public String getFullName() {
        return firstName + " " + lastName ;
    }

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return Arrays.asList(new SimpleGrantedAuthority(role.getName())) ;
//    }
//
//    @Override
//    public String getPassword() {
//        return password;
//    }
//
//    @Override
//    public String getUsername() {
//        return email;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return status;
//    }

    public User(Long id) {
        this.id = id;
    }
}

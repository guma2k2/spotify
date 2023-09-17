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

import java.util.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "`user`")
@Builder
public class User implements UserDetails {

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

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] photo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    @JsonBackReference
    private Role role ;

    @Enumerated(EnumType.STRING)
    private Gender gender ;

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


    @Transient
    public String getUserPhotoPath() {
        String baseUrl = FileUploadUtil.baseUrl;
        if(photo!=null) {
            return baseUrl+"/user/viewPhoto/" + this.id ;
        }
        return FileUploadUtil.baseUrlFail;
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


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority(role.getName())) ;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public User(Long id) {
        this.id = id;
    }
}

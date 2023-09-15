package com.spotify.app.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category")
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;
    @Column(length = 20, unique = true)
    private String title ;
    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paren_id")
    private Category categoryParent;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "categoryParent")
    @Builder.Default
    private Set<Category> categoriesChildren = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "category_playlist",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "playlist_id")
    )
    @Builder.Default
    @JsonManagedReference
    private Set<Playlist> playlists = new HashSet<>();


    public void addChild(Category category) {
        this.getCategoriesChildren().add(category) ;
    }
    public void addPlaylist(Playlist playlist) {
        playlists.add(playlist);
        playlist.getCategories().add(this);
    }

    public void removePlaylist(Playlist playlist) {
        playlists.remove(playlist);
        playlist.getCategories().remove(this);
    }
}

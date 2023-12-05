package com.spotify.app.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.spotify.app.utility.FileUploadUtil;
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
    @Column(length = 200)
    private String title ;

    private String image;

    private String thumbnail;

    private boolean status;


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


    @Transient
    public String getImagePath() {
        if(image!=null) {
            return image;
        }
        return FileUploadUtil.baseUrlPlaylistImage;
    }
    @Transient
    public String getThumbnailPath() {
        if(thumbnail!=null) {
            return thumbnail;
        }
        return FileUploadUtil.baseUrlPlaylistImage;
    }


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


    @Transient
    public String getCategoryParentTitle() {
        if(this.categoryParent != null) {
            return this.categoryParent.getTitle();
        }
        return null;
    }
}

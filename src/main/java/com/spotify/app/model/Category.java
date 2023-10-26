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
        String baseUrl = FileUploadUtil.baseUrl;
        if(image!=null) {
            return baseUrl+"/category-images/" + this.id +"/"+image ;
        }
        return FileUploadUtil.baseUrlFail;
    }
    @Transient
    public String getThumbnailPath() {
        String baseUrl = FileUploadUtil.baseUrl;
        if(thumbnail!=null) {
            return baseUrl+"/category-thumbnails/" + this.id + "/" +thumbnail ;
        }
        return FileUploadUtil.baseUrlFail;
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

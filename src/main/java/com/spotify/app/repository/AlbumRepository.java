package com.spotify.app.repository;

import com.spotify.app.model.Album;
import com.spotify.app.model.Playlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlbumRepository  extends JpaRepository<Album, Long> {

    @Query("""
            SELECT a
            FROM Album a
            LEFT JOIN FETCH a.user u
            LEFT JOIN FETCH u.role r
            LEFT JOIN FETCH a.albumSongList as
            WHERE a.id = :albumId
            """)
    Optional<Album> findByIdReturnSongs(@Param("albumId") Long albumId);


    @Query("""
            SELECT a
            FROM Album a
            LEFT JOIN FETCH a.user u
            WHERE a.id = :albumId
            """)
    Optional<Album> findByIdReturnUser(@Param("albumId") Long albumId);



    @Query("""
            SELECT a
            FROM Album a
            LEFT JOIN FETCH a.user u
            WHERE u.id = :userId
            """)
    List<Album> findByUserId(@Param("userId") Long userId);

    @Query("""
            SELECT a
            FROM Album a
            LEFT JOIN FETCH a.user u
            WHERE a.name LIKE CONCAT('%', upper(:name), '%')
            """)
    Page<Album> findAllByName(@Param("name")String name, Pageable pageable) ;

}

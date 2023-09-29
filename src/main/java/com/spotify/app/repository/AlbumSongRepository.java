package com.spotify.app.repository;

import com.spotify.app.model.AlbumSong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumSongRepository extends JpaRepository<AlbumSong,Long> {

    @Query("""
            SELECT as
            FROM AlbumSong as
            JOIN FETCH as.album a
            JOIN FETCH as.song s
            JOIN FETCH s.users u
            WHERE a.id = :id
            """)
    List<AlbumSong> findByAlbumId(@Param("id") Long id) ;

    @Query("""
            SELECT ab
            FROM AlbumSong ab
            LEFT JOIN FETCH ab.album a
            LEFT JOIN FETCH ab.song s
            LEFT JOIN FETCH s.users
            WHERE s.id = :id
            """)
    List<AlbumSong> findBySongId(@Param("id") Long id) ;
}

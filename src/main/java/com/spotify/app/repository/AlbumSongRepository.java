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
            JOIN FETCH as.song
            WHERE a.id = :id
            """)
    List<AlbumSong> findByAlbumId(@Param("id") Long id) ;

    @Query("""
            SELECT as
            FROM AlbumSong as
            JOIN FETCH as.album
            JOIN FETCH as.song s
            WHERE s.id = :id
            """)
    List<AlbumSong> findBySongId(@Param("id") Long id) ;
}

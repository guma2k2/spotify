package com.spotify.app.repository;

import com.spotify.app.model.Playlist;
import com.spotify.app.model.PlaylistSong;
import com.spotify.app.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistSongRepository extends JpaRepository<Playlist, Song> {


    @Query("""
            SELECT ps
            FROM PlaylistSong ps
            LEFT JOIN FETCH ps.playlist p
            LEFT JOIN FETCH ps.song s
            LEFT JOIN FETCH s.albumSongList as
            WHERE p.id = :id
            """)
    List<PlaylistSong> findByPlaylistId(@Param("id") Long id) ;

    @Query("""
            SELECT ps
            FROM PlaylistSong ps
            JOIN FETCH ps.playlist p
            JOIN FETCH ps.song s
            WHERE p.id = :id
            """)
    List<PlaylistSong> findByPlaylistIdCustom(@Param("id") Long id) ;
}

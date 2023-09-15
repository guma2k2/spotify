package com.spotify.app.repository;

import com.spotify.app.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {


    @Query("SELECT s FROM Song s " +
            "JOIN FETCH s.users u " +
            "WHERE s.id = :songId")
    Optional<Song> findByIdCustom( @Param("songId") Long songId ) ;

    @Query("""
            SELECT s
            FROM Song s
            WHERE EXISTS (
                SELECT ps.song
                FROM PlaylistSong ps
                INNER JOIN ps.playlist p
                WHERE p.id = :playlistId
            )
            """)
    List<Song> findByPlaylistId( @Param("playlistId") Long playlistId ) ;


}

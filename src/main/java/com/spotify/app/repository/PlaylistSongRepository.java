package com.spotify.app.repository;

import com.spotify.app.model.Playlist;
import com.spotify.app.model.PlaylistSong;
import com.spotify.app.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaylistSongRepository extends JpaRepository<Playlist, Song> {


    @Query("""
            SELECT ps
            FROM PlaylistSong ps
            LEFT JOIN FETCH ps.playlist p
            LEFT JOIN FETCH ps.song s
            LEFT JOIN FETCH s.albumSongList as
            WHERE p.id = :id AND s.status = true
            """)
    List<PlaylistSong> findByPlaylistId(@Param("id") Long id) ;



    @Query("""
            SELECT ps
            FROM PlaylistSong ps
            LEFT JOIN FETCH ps.playlist p
            LEFT JOIN FETCH ps.song s
            WHERE p.id = :playlistId AND s.id = :songId
            """)
    Optional<PlaylistSong> findBySongIdAndPlaylistId(@Param("songId") Long songId,
                                                     @Param("playlistId") Long playlistId);

}

package com.spotify.app.repository;

import com.spotify.app.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {


    @Query("""
            SELECT s
            FROM Song s
            LEFT JOIN FETCH s.users u
            LEFT JOIN FETCH s.albumSongList
            WHERE s.id = :songId
            """)
    Optional<Song> findByIdReturnUsersAlbums( @Param("songId") Long songId ) ;




    @Query("""
            SELECT s
            FROM Song s
            LEFT JOIN FETCH s.users u
            LEFT JOIN FETCH s.albumSongList
            WHERE s.label = :label
            """)
    List<Song> findByLabelReturnUsersAlbums( @Param("label") String label ) ;

    @Query("""
            SELECT s
            FROM Song s
            LEFT JOIN FETCH s.users
            WHERE s.name LIKE %:name%
            """)
    List<Song> findByNameFullText(@Param("name") String name);



    @Query("""
            UPDATE Song s
            SET s.status = :status
            WHERE s.id = :songId
            """)
    @Modifying
    void updateStatus(@Param("songId") Long songId, @Param("status") boolean status);

    Optional<Song> findByName(String name);




}

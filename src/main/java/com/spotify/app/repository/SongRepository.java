package com.spotify.app.repository;

import com.spotify.app.model.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {


    @Query("""
            SELECT s
            FROM Song s
            LEFT JOIN FETCH s.users u
            LEFT JOIN FETCH s.albumSongList
            WHERE s.id = :songId
            """)
    Optional<Song> findByIdReturnUsersAlbumsReviews( @Param("songId") Long songId ) ;




    @Query("""
            SELECT s
            FROM Song s
            LEFT JOIN FETCH s.users u
            LEFT JOIN FETCH s.albumSongList
            WHERE s.label = :label
            """)
    List<Song> findByLabelReturnUsersAlbums( @Param("label") String label) ;

    @Query("""
            SELECT s
            FROM Song s
            LEFT JOIN FETCH s.users
            LEFT JOIN FETCH s.albumSongList
            WHERE s.name LIKE %:name%
            """)
    Page<Song> findByNameFullText(@Param("name") String name, Pageable pageable);



    @Query("""
            UPDATE Song s
            SET s.viewCount = s.viewCount + 1
            WHERE s.id = :songId
            """)
    @Modifying
    void updateViewCount(@Param("songId") Long songId);

    Optional<Song> findByName(String name);


}

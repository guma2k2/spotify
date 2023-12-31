package com.spotify.app.repository;

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
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {



    @Query("""
            SELECT p
            FROM Playlist p
            LEFT JOIN FETCH p.playlistUserList
            WHERE p.id = :id
            """)
    Optional<Playlist> findByIdReturnPlaylistUsers(@Param("id") Long id);


    @Query("""
            SELECT p
            FROM Playlist p
            LEFT JOIN FETCH p.playlistUserList u
            WHERE p.name LIKE CONCAT('%', upper(:name), '%')
            """)
    Page<Playlist> findAllByName(@Param("name")String name, Pageable pageable) ;
}

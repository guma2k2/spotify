package com.spotify.app.repository;

import com.spotify.app.model.Album;
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
            LEFT JOIN FETCH a.user
            WHERE a.id = :albumId
            """)
    Optional<Album> findByIdCustom(@Param("albumId") Long albumId);
}

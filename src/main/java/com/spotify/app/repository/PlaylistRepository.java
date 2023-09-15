package com.spotify.app.repository;

import com.spotify.app.model.Playlist;
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
            LEFT JOIN FETCH p.user u
            WHERE u.id = :userId
            """)
    List<Playlist> findByUserId(@Param("userId") Long userId);

}

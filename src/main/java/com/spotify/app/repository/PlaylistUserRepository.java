package com.spotify.app.repository;

import com.spotify.app.model.PlaylistUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistUserRepository extends JpaRepository<PlaylistUser, Long> {

    @Query("""
            DELETE
            FROM PlaylistUser pu
            WHERE  pu.user.id = :userId AND pu.playlist.id = :playlistId
            """)
    @Modifying
    void deleteByUserAndPlaylist(@Param("userId") Long userId,
                                        @Param("playlistId") Long playlistId);
}

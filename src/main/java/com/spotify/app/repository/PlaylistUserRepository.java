package com.spotify.app.repository;

import com.spotify.app.model.PlaylistUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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


    @Query("""
            SELECT pu
            FROM PlaylistUser pu
            LEFT JOIN FETCH pu.user u
            LEFT JOIN FETCH pu.playlist p 
            WHERE u.id = :userId
            """)
    List<PlaylistUser> findByUserid(@Param("userId") Long userId);

    @Query("""
            SELECT pu
            FROM PlaylistUser pu
            LEFT JOIN FETCH pu.user u
            LEFT JOIN FETCH pu.playlist p
            WHERE u.id = :userId AND p.name <> 'Liked Songs'
            """)
    List<PlaylistUser> findByUseridWithoutLikedSong(@Param("userId") Long userId);


    @Query("""
            SELECT pu
            FROM PlaylistUser pu
            LEFT JOIN FETCH pu.user u
            LEFT JOIN FETCH pu.playlist p
            WHERE u.id = :userId AND p.name = :playlistName
            """)
    Optional<PlaylistUser> findByUserIdAndName(@Param("userId") Long userId,
                                               @Param("playlistName") String playlistName);

    @Query("""
            SELECT pu
            FROM PlaylistUser pu
            LEFT JOIN FETCH pu.user u
            LEFT JOIN FETCH pu.playlist p
            WHERE u.id = :userId AND p.id = :playlistId
            """)
    Optional<PlaylistUser> findByUserAndPlaylist(@Param("userId") Long userID,
                                                 @Param("playlistId") Long playlistId);


}

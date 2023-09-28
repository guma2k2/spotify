package com.spotify.app.repository;

import com.spotify.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("""
            
            SELECT u FROM User u
            JOIN FETCH u.role r
            WHERE u.email = :email
            
            """)
    Optional<User> findByEmail(@Param("email") String email) ;

    @Query("""
            SELECT u
            FROM User u
            JOIN FETCH u.role r
            WHERE u.id = :id
            """)
    Optional<User> findById(@Param("id") Long id) ;


    @Query("""
            SELECT u
            FROM User u
            JOIN FETCH u.role
            """)
    List<User> findAllCustom();


    @Query("""
            SELECT u
            FROM User u
            LEFT JOIN FETCH u.role r
            LEFT JOIN FETCH u.songs s
            WHERE u.id = :userId
           """)
    Optional<User> findByIdReturnRoleAndSongs(@Param("userId") Long userId);

    @Query("""
            SELECT u
            FROM User u
            LEFT JOIN FETCH u.followingList s
            LEFT JOIN FETCH u.playlistUserList p
            LEFT JOIN FETCH u.role
            WHERE u.id = :userId
           """)
    Optional<User> findByIdReturnFollowingsAndPlaylists(@Param("userId") Long userId);


}

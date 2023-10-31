package com.spotify.app.repository;

import com.spotify.app.model.Song;
import com.spotify.app.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
            JOIN FETCH u.role
            """)
    List<User> findAllCustom();

    @Query("""
            SELECT u
            FROM User u
            JOIN FETCH u.role r
            WHERE r.name = 'ROLE_ARTIST' AND
             CONCAT(upper(u.firstName), ' ', upper(u.lastName)) LIKE CONCAT('%', upper(:userName), '%')
            """)
    Page<User> findAllArtistByUserName(Pageable pageable, @Param("userName") String userName);


    @Query("""
        SELECT u
        FROM User u
        JOIN FETCH u.role
        WHERE CONCAT(upper(u.firstName), upper(u.lastName), upper(u.email)) LIKE CONCAT('%', upper(:keyword), '%')
        """)
    Page<User> findAllWithKeyword(@Param("keyword") String keyword, Pageable pageable);



    @Query("""
            SELECT u
            FROM User u
            LEFT JOIN FETCH u.role r
            LEFT JOIN FETCH u.songs s
            WHERE u.id = :userId
           """)
    Optional<User> findByIdReturnRoleAndSongs(@Param("userId") Long userId);

}

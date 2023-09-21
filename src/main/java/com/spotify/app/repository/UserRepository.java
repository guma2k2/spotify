package com.spotify.app.repository;

import com.spotify.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("""
            
            SELECT u FROM User u
            JOIN FETCH u.role r
            WHERE u.email = :email
            
            """)
    Optional<User> findByEmail(@Param("email") String email) ;


    @Query("""
            
            SELECT u FROM User u
            LEFT JOIN FETCH u.role r
            LEFT JOIN FETCH u.songs s
            WHERE u.id = :userId
            
           """)
    Optional<User> findByIdCustom(@Param("userId") Long userId);

}

package com.spotify.app.repository;

import com.spotify.app.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {

    @Query("""
            SELECT r
            FROM Review r
            LEFT JOIN FETCH r.user u
            LEFT JOIN FETCH r.song s
            WHERE s.id = :songId
            """)
    List<Review> findBySongId(@Param("songId") Long songId);


    @Query("""
            SELECT r
            FROM Review r
            LEFT JOIN FETCH r.user u
            LEFT JOIN FETCH r.song s
            """)
    List<Review> findAllReturnUser();

}
